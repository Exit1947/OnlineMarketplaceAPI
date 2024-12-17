package com.onlineShop.service.serviceImpl;

import com.onlineShop.converter.product.media.MediaConverter;
import com.onlineShop.dto.product.media.MediaProductRequest;
import com.onlineShop.dto.product.media.MediaProductResponse;
import com.onlineShop.models.Product.Media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MediaProductApiServiceImpl {

    private final MediaProductServiceImpl mediaService;

    @Autowired
    public MediaProductApiServiceImpl(MediaProductServiceImpl mediaService) {
        this.mediaService = mediaService;
    }


    @Transactional
    public ResponseEntity<HttpStatus> saveMedia(String entityId, MultipartFile mediaFile) {
        if(mediaService.saveMedia(entityId, mediaFile)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<HttpStatus> saveThumbnail(String entityId, MultipartFile thumbnailImage) {
        if(mediaService.saveThumbnail(entityId, thumbnailImage)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<HttpStatus> initialSave(String entityId, List<MultipartFile> media) {
        if(mediaService.initialSave(entityId, media)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<MediaProductResponse>> getAllForEntity(String productId) {
        List<MediaProductResponse> mediaProductResponseList = mediaService.getAllForEntity(productId).stream()
                .map(MediaConverter::toMediaProductResponse)
                .toList();

        mediaProductResponseList
                .forEach(media -> media.setMediaUrl(mediaService.getUrl(media.getMediaUrl())));

        return new ResponseEntity<>(mediaProductResponseList, HttpStatus.OK);
    }


    public ResponseEntity<MediaProductResponse> getById(String id) {
        Optional<Media> media = mediaService.getById(id);
        return media.map(value -> new ResponseEntity<>(MediaConverter.toMediaProductResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    public ResponseEntity<MediaProductResponse> getByMediaName(String mediaName) {
        Optional<Media> media = mediaService.getByMediaName(mediaName);
        return media.map(value -> new ResponseEntity<>(MediaConverter.toMediaProductResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    @Transactional
    public ResponseEntity<HttpStatus> update(MediaProductRequest mediaProductRequest){
        if (mediaService.update(MediaConverter.toMedia(mediaProductRequest))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Transactional
    public ResponseEntity<HttpStatus> deleteMedia(String mediaId) {
        if(mediaService.deleteMediaById(mediaId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<HttpStatus> deleteAll(List<MediaProductRequest> mediaRequests) {
        List<Media> mediaList = mediaRequests.stream()
                .map(MediaConverter::toMedia)
                .filter(Objects::nonNull)
                .toList();
        if (mediaService.deleteAll(mediaList)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<HttpStatus> deleteAllForEntity(String productId) {
        List<Media> mediaProductResponseList = mediaService.getAllForEntity(productId);
        if(mediaProductResponseList != null){
            mediaService.deleteAll(mediaProductResponseList);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
