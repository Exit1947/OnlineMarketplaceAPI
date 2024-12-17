package com.onlineShop.service.serviceImpl;

import com.onlineShop.dto.media.MediaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AvatarUserEntityApiServiceImpl {

    private final AvatarUserEntityServiceImpl avatarUserEntityService;

    @Autowired
    public AvatarUserEntityApiServiceImpl(AvatarUserEntityServiceImpl avatarUserEntityService) {
        this.avatarUserEntityService = avatarUserEntityService;
    }

    public ResponseEntity<HttpStatus> save(String entityId, MultipartFile mediaFile) {
        if(avatarUserEntityService.save(entityId, mediaFile)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<MediaResponse> getByMediaName(String mediaName) {
        String url = avatarUserEntityService.getUrl(mediaName);
        if(url!=null){
            return new ResponseEntity<>(new MediaResponse(url), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<MediaResponse> getForEntity(String userId) {
        String url = avatarUserEntityService.getUrl(avatarUserEntityService.getForEntity(userId));
        if(url!=null){
            return new ResponseEntity<>(new MediaResponse(url), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<HttpStatus> delete(String userId) {
        if(avatarUserEntityService.delete(userId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
