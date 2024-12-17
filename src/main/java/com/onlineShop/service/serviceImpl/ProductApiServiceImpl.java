package com.onlineShop.service.serviceImpl;

import com.onlineShop.converter.product.ProductConverter;
import com.onlineShop.models.Product.Characteristic.Characteristic;
import com.onlineShop.models.Product.Characteristic.ProductCharacteristic;
import com.onlineShop.dto.product.CharacteristicDto;
import com.onlineShop.dto.product.*;
import com.onlineShop.dto.product.ProductCardInfoResponse;
import com.onlineShop.dto.product.media.MediaProductRequest;
import com.onlineShop.dto.product.media.MediaProductResponse;
import com.onlineShop.models.Product.DiscountProduct;
import com.onlineShop.models.Product.Media.Media;
import com.onlineShop.models.Product.Product;
import com.onlineShop.repository.CharacteristicRepository;
import com.onlineShop.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductApiServiceImpl implements ProductApiService {

    private final ProductService productService;
    private final FeedbackService feedbackService;
    private final MediaProductApiServiceImpl mediaProductApiService;
    private final CharacteristicRepository characteristicRepository;

    @Autowired
    public ProductApiServiceImpl(final MediaProductApiServiceImpl mediaProductApiService, final FeedbackService feedbackService,
                                 final ProductService productService, final CharacteristicRepository characteristicRepository) {
        this.mediaProductApiService = mediaProductApiService;
        this.feedbackService = feedbackService;
        this.productService = productService;
        this.characteristicRepository = characteristicRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<String> save(final ProductRequest productRequest) {
            Optional<Product> existingProduct = productService.getByTitle(productRequest.getTitle());
            if (existingProduct.isEmpty()) {
                Product product = ProductConverter.toProduct(productRequest);

                if (productRequest.getCharacteristicValuesList() != null) {
                    List<ProductCharacteristic>productCharacteristicsList = checkAndGetProductCharacteristicList(productRequest.getCharacteristicValuesList(), product);
                    if(productCharacteristicsList.isEmpty()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    product.setCharacteristicValues(productCharacteristicsList);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                productService.saveProduct(product);

                if (product.isDiscount()) {
                    productService.saveDiscount(ProductConverter.toDiscountProduct(product, productRequest));
                }

                return new ResponseEntity<>(product.getId(), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<ProductResponse> getById(final String id) {
        Optional<Product> existingProduct = productService.getById(id);
        return getProductResponseEntity(existingProduct);
    }

    @Override
    public ResponseEntity<List<ProductCardInfoResponse>> getRandomProductCardsById(int count) {
        return ResponseEntity.ok(productService.getRandomProductCardsById(count)
                .stream().map(this::convertToProductResponse)
                .toList());
    }

    @Override
    public ResponseEntity<ProductCardInfoResponse> getProductCardInfoById(final String id) {
        Optional<Product> existingProduct = productService.getById(id);
        return getProductCardInfoResponseResponseEntity(existingProduct);
    }

    @Override
    public ResponseEntity<ProductResponse> getByTitle(final String title) {
        Optional<Product> existingProduct = productService.getByTitle(title);
        return getProductResponseEntity(existingProduct);
    }

    @Override
    public ResponseEntity<ProductCardInfoResponse> getProductCardInfoByTitle(String title) {
        Optional<Product> existingProduct = productService.getByTitle(title);
        return getProductCardInfoResponseResponseEntity(existingProduct);
    }

    private ResponseEntity<ProductResponse> getProductResponseEntity(Optional<Product> existingProduct) {
        if(existingProduct.isPresent()) {
            Product product = existingProduct.get();

            ProductResponse productResponse = ProductConverter.toProductResponse(product, getDiscount(product), productService.getAllForEntityById(product.getId()));

            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ProductCardInfoResponse> getProductCardInfoResponseResponseEntity(Optional<Product> existingProduct) {
        return existingProduct.map(product -> new ResponseEntity<>(convertToProductResponse(product), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ProductCardInfoResponse convertToProductResponse(Product product) {
        return ProductConverter.toProductCardResponse(product, getDiscount(product),
                feedbackService.getAllFeedbacksForProduct(product.getId()).size(), productService.thumbnailImageUrl(product.getThumbnailImage()));
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> update(final ProductRequest productRequest) {
        Optional<Product> existingProduct = productService.getById(productRequest.getId());
        if(existingProduct.isPresent()){
            Product product = existingProduct.get();
            Product updateProduct = ProductConverter.toProduct(productRequest);

            if(productRequest.getCharacteristicValuesList()!=null) {
                List<ProductCharacteristic> productCharacteristicsList =
                        checkAndUpdateProductCharacteristicList(product.getCharacteristicValues(), productRequest.getCharacteristicValuesList(), updateProduct);
                if (!productCharacteristicsList.isEmpty()) {
                    updateProduct.setCharacteristicValues(productCharacteristicsList);
                }
                else{
                    updateProduct.setCharacteristicValues(product.getCharacteristicValues());
                }
            }
            else{
                updateProduct.setCharacteristicValues(product.getCharacteristicValues());
            }

            productService.saveProduct(updateProduct);

            Optional<DiscountProduct> existingDiscountProduct = productService.getDiscountByProductId(updateProduct.getId());
            if(existingDiscountProduct.isPresent()){
                if(updateProduct.isDiscount()) {
                    DiscountProduct discountProduct = ProductConverter.toDiscountProduct(updateProduct, productRequest);
                    discountProduct.setId(existingDiscountProduct.get().getId());

                    productService.saveDiscount(discountProduct);
                }
                else{
                    productService.deleteDiscount(existingDiscountProduct.get().getId());
                }
            }
            else{
                if(updateProduct.isDiscount()) {
                    productService.saveDiscount(ProductConverter.toDiscountProduct(updateProduct, productRequest));
                }
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> delete(final String id) {
        if(productService.deleteById(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> saveMedia(final String productId, final MultipartFile mediaFile) {
        return mediaProductApiService.saveMedia(productId, mediaFile);
    }

    @Override
    public ResponseEntity<HttpStatus> saveThumbnail(String productId, MultipartFile thumbnailImage) {
        return mediaProductApiService.saveThumbnail(productId, thumbnailImage);
    }

    @Override
    public ResponseEntity<HttpStatus> initialSaveMedia(final String productId, final List<MultipartFile> media) {
        return mediaProductApiService.initialSave(productId, media);
    }

    @Override
    public ResponseEntity<List<MediaProductResponse>> getAllMediasForEntity(final String productId) {
        return mediaProductApiService.getAllForEntity(productId);
    }

    @Override
    public ResponseEntity<MediaProductResponse> getByMediaName(final String mediaName) {
        return mediaProductApiService.getByMediaName(mediaName);
    }

    @Override
    public ResponseEntity<HttpStatus> updateMedia(final MediaProductRequest mediaProductRequest) {
        return mediaProductApiService.update(mediaProductRequest);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteMedia(final String mediaId) {
        return mediaProductApiService.deleteMedia(mediaId);
    }

    private int getDiscount(Product product){
        int discount = 0;
        if(product.isDiscount()) {
            Optional<DiscountProduct> discountProduct = productService.getDiscountByProductId(product.getId());
            if(discountProduct.isPresent()){
                return discountProduct.get().getDiscount();
            }
        }

        return discount;
    }

    private List<ProductCharacteristic> checkAndGetProductCharacteristicList(List<CharacteristicDto> characteristicDtoList, Product product) {
        AtomicInteger index = new AtomicInteger(1);
        return Optional.of(characteristicDtoList.stream()
                                .flatMap(characteristicDto -> {
                                    Optional<Characteristic> existingCharacteristic = characteristicRepository.findByName(characteristicDto.getName());
                                    return existingCharacteristic.stream()
                                            .map(characteristic -> createProductCharacteristic(index, characteristicDto, characteristic, product));
                                })
                                .toList())
                        .orElse(Collections.emptyList());
    }
    private ProductCharacteristic createProductCharacteristic(AtomicInteger index, CharacteristicDto characteristicDto, Characteristic characteristic, Product product) {
        ProductCharacteristic productCharacteristic = new ProductCharacteristic();
        productCharacteristic.setProduct(product);
        productCharacteristic.setCharacteristic(characteristic);
        productCharacteristic.setValue(characteristicDto.getValue());
        productCharacteristic.setNumber(index.getAndIncrement());

        return productCharacteristic;
    }

    private List<ProductCharacteristic> checkAndUpdateProductCharacteristicList(final List<ProductCharacteristic> existingCharacteristics, final List<CharacteristicDto> characteristicDtoList, final Product updateProduct) {
        AtomicInteger index = new AtomicInteger(1);
        List<ProductCharacteristic> finalList = new ArrayList<>(existingCharacteristics);

        List<ProductCharacteristic> newCharacteristicsList = characteristicDtoList.stream()
                .filter(dto -> existingCharacteristics.stream()
                        .noneMatch(pc -> pc.getCharacteristic().getName().equals(dto.getName())))
                .map(dto -> {
                    Optional<Characteristic> existingCharacteristic = characteristicRepository.findByName(dto.getName());
                    return existingCharacteristic.map(characteristic -> createProductCharacteristic(index, dto, characteristic, updateProduct))
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .toList();

        finalList.addAll(newCharacteristicsList);

        List<ProductCharacteristic> updatedCharacteristicsList = new ArrayList<>();

        //TO DO
        for(ProductCharacteristic productCharacteristic : existingCharacteristics) {
            List<CharacteristicDto> matchingDtos = characteristicDtoList.stream()
                    .filter(dto -> dto.getName().equals(productCharacteristic.getCharacteristic().getName()))
                    .toList();

            if (!matchingDtos.isEmpty()) {
                for (CharacteristicDto matchingDto : matchingDtos) {
                    ProductCharacteristic productCharacteristic1 = new ProductCharacteristic();
                    productCharacteristic1.setValue(matchingDto.getValue());
                    productCharacteristic1.setNumber(index.getAndIncrement());

                    updatedCharacteristicsList.add(productCharacteristic);
                }
            } else {
                finalList.remove(productCharacteristic);
            }
        }

        finalList.addAll(updatedCharacteristicsList);

        if(finalList.isEmpty()){
            return Collections.emptyList();
        }

        return finalList;
    }

}
