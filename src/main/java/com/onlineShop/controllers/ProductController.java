package com.onlineShop.controllers;

import com.onlineShop.dto.product.ProductCardInfoResponse;
import com.onlineShop.dto.product.ProductRequest;
import com.onlineShop.dto.product.ProductResponse;
import com.onlineShop.dto.product.media.MediaProductRequest;
import com.onlineShop.dto.product.media.MediaProductResponse;
import com.onlineShop.service.ProductApiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductApiService productApiService;

    @Autowired
    public ProductController(final ProductApiService productApiService) {
        this.productApiService = productApiService;
    }

    @PostMapping
    public @ResponseBody ResponseEntity<String> save(@RequestBody @Valid ProductRequest product) {
        return productApiService.save(product);
    }

    @GetMapping("/id={id}")
    public @ResponseBody ResponseEntity<ProductResponse> getById(@PathVariable("id") String id) {
        if(id!=null && !id.isEmpty()) {
            return productApiService.getById(id);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/card/id={id}")
    public @ResponseBody ResponseEntity<ProductCardInfoResponse> getProductCardInfoById(@PathVariable("id") String id) {
        if(id!=null && !id.isEmpty()) {
            return productApiService.getProductCardInfoById(id);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/title={title}")
    public @ResponseBody ResponseEntity<ProductResponse> getByTitle(@PathVariable("title") String title) {
        if(title!=null && !title.isEmpty()) {
            return productApiService.getByTitle(title);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/card/title={title}")
    public @ResponseBody ResponseEntity<ProductCardInfoResponse> getProductCardInfoByTitle(@PathVariable("title") String title) {
        if(title!=null && !title.isEmpty()) {
            return productApiService.getProductCardInfoByTitle(title);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/card/count={count}")
    public @ResponseBody ResponseEntity<List<ProductCardInfoResponse>> getRandomProductCardsById(@PathVariable("count") int count) {
        if(count > 0) {
            return productApiService.getRandomProductCardsById(count);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping()
    public @ResponseBody ResponseEntity<HttpStatus> update(@RequestBody @Valid ProductRequest product) {
        return productApiService.update(product);
    }

    @DeleteMapping("/id={id}")
    public @ResponseBody ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        if(id!=null && !id.isEmpty()) {
            return productApiService.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/media/product-id={productId}")
    public @ResponseBody ResponseEntity<HttpStatus> saveMedia(@PathVariable("productId") String productId, @RequestPart("mediaFile") MultipartFile mediaFile) {
        return productApiService.saveMedia(productId, mediaFile);
    }

    @PostMapping("/media/initial-save-media/product-id={productId}")
    public @ResponseBody ResponseEntity<HttpStatus> initialSaveMedia(@PathVariable("productId") String productId, @RequestPart("mediaFiles") List<MultipartFile> mediaFiles) {
        return productApiService.initialSaveMedia(productId, mediaFiles);
    }

    @PostMapping("/media/thumbnail/product-id={productId}")
    public @ResponseBody ResponseEntity<HttpStatus> saveThumbnail(@PathVariable("productId") String productId, @RequestPart("thumbnailImage") MultipartFile thumbnailImage) {
        return productApiService.saveThumbnail(productId, thumbnailImage);
    }

    @GetMapping("/media/product-id={productId}")
    public ResponseEntity<List<MediaProductResponse>> getAllForEntity(@PathVariable("productId") String productId){
        return productApiService.getAllMediasForEntity(productId);
    }

    @GetMapping("/media/media-name={mediaName}")
    public ResponseEntity<MediaProductResponse> getByMediaName(@PathVariable("mediaName") String mediaName){
        return productApiService.getByMediaName(mediaName);
    }

    @PutMapping("/media")
    public @ResponseBody ResponseEntity<HttpStatus> updateMedia(@RequestBody @Valid MediaProductRequest mediaProductRequest) {
        return productApiService.updateMedia(mediaProductRequest);
    }

    @DeleteMapping("/media/media-id={mediaId}")
    public @ResponseBody ResponseEntity<HttpStatus> deleteMedia(@PathVariable("mediaId") String mediaId) {
        return productApiService.deleteMedia(mediaId);
    }

}
