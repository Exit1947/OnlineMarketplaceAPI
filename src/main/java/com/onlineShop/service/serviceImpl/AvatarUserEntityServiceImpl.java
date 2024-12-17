package com.onlineShop.service.serviceImpl;

import com.onlineShop.models.Users.UserEntity;
import com.onlineShop.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class AvatarUserEntityServiceImpl {

    private final UserEntityService userEntityService;
    private final AmazonS3CloudServiceImpl amazonS3CloudService;

    @Autowired
    public AvatarUserEntityServiceImpl(UserEntityService userEntityService, AmazonS3CloudServiceImpl amazonS3CloudService) {
        this.userEntityService = userEntityService;
        this.amazonS3CloudService = amazonS3CloudService;
    }

    @Transactional
    public boolean save(String entityId, MultipartFile mediaFile) {
        Optional<UserEntity> existingUser = userEntityService.findById(entityId);
        if(existingUser.isPresent()) {
            UserEntity userEntity = existingUser.get();

            if(userEntity.getAvatar() != null){
                amazonS3CloudService.delete(userEntity.getAvatar());
            }

            String extension = mediaFile.getOriginalFilename().substring(mediaFile.getOriginalFilename().lastIndexOf('.')).toLowerCase();
            String mediaName = UUID.randomUUID() + extension;
            userEntity.setAvatar(mediaName);

            try {
                amazonS3CloudService.store(userEntity.getAvatar(), mediaFile.getBytes());
                return true;
            } catch (IOException ignored) {
                return false;
            }
        }
        return false;
    }

    public String getUrl(String name) {
        if(name !=null) {
            return amazonS3CloudService.get(name);
        }
        return null;
    }

    public String getForEntity(String userId){
        Optional<UserEntity> userEntity = userEntityService.findById(userId);
        return userEntity.map(UserEntity::getAvatar).orElse(null);
    }

    @Transactional
    public boolean delete(String userId) {
        Optional<UserEntity> existingUser = userEntityService.findById(userId);
        if(existingUser.isPresent()) {
            UserEntity userEntity = existingUser.get();
            String avatar = userEntity.getAvatar();
            if(avatar != null) {
                userEntity.setAvatar(null);
                userEntityService.save(userEntity);

                amazonS3CloudService.delete(avatar);
            }

            return true;
        }
        return false;
    }

}
