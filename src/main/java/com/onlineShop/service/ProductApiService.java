package com.onlineShop.service;

import com.onlineShop.converter.product.media.MediaConverter;
import com.onlineShop.dto.product.ProductCardInfoResponse;
import com.onlineShop.dto.product.ProductRequest;
import com.onlineShop.dto.product.ProductResponse;
import com.onlineShop.dto.product.media.MediaProductRequest;
import com.onlineShop.dto.product.media.MediaProductResponse;
import com.onlineShop.models.Product.Media.Media;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface ProductApiService {

    ResponseEntity<String> save(ProductRequest productRequest);

    ResponseEntity<ProductResponse> getById(String id);

    ResponseEntity<List<ProductCardInfoResponse>> getRandomProductCardsById(int count);

    ResponseEntity<ProductCardInfoResponse> getProductCardInfoById(String id);

    ResponseEntity<ProductResponse> getByTitle(String title);

    ResponseEntity<ProductCardInfoResponse> getProductCardInfoByTitle(String title);

    ResponseEntity<HttpStatus> update(ProductRequest product);

    ResponseEntity<HttpStatus> delete(String id);

    ResponseEntity<HttpStatus> saveMedia(String productId, MultipartFile mediaFile);

    ResponseEntity<HttpStatus> saveThumbnail(String productId, MultipartFile thumbnailImage);

    ResponseEntity<HttpStatus> initialSaveMedia(String productId, List<MultipartFile> media);

    ResponseEntity<List<MediaProductResponse>> getAllMediasForEntity(String productId);

    ResponseEntity<MediaProductResponse> getByMediaName(String mediaName);

    ResponseEntity<HttpStatus> updateMedia(MediaProductRequest mediaProductRequest);

    ResponseEntity<HttpStatus> deleteMedia(String mediaId);

}