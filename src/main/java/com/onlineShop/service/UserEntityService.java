package com.onlineShop.service;

import com.onlineShop.dto.user.userEntity.userEntity.UserEntityRequest;
import com.onlineShop.models.Users.UserEntity;
import com.onlineShop.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;

    @Autowired
    public UserEntityService(final UserEntityRepository personRepository) {
        this.userEntityRepository = personRepository;
    }

    public Optional<UserEntity> findById(final String id) {
        return userEntityRepository.findById(id);
    }

    public Optional<UserEntity> findByEmail(final String email) {
        return userEntityRepository.findByEmail(email);
    }

    public Optional<UserEntity> findByLogin(final String login) {
        return userEntityRepository.findByLogin(login);
    }

    public Optional<UserEntity> findByPhoneNumber(final String phoneNumber) {
        return userEntityRepository.findByPhoneNumber(phoneNumber);
    }

    public void save(final UserEntity userEntity) {
        userEntityRepository.save(userEntity);
    }

    public boolean existsById(final String id) {
        return userEntityRepository.existsById(id);
    }

    public void delete(final String id) {
        userEntityRepository.deleteById(id);
    }

    public boolean existsByEmail(final String email) {
        return userEntityRepository.existsUserEntitiesByEmail(email);
    }

    public boolean existByLogin(final String login){
        return userEntityRepository.existsUserEntitiesByLogin(login);
    }

    public boolean existByPhoneNumber(final String phoneNumber) {
        return userEntityRepository.existsUserEntitiesByPhoneNumber(phoneNumber);
    }

    public boolean exist(String email, String login, String phoneNumber) {
        if(email!=null){
            if (existsByEmail(email)) {
                return true;
            }
        }

        if(login != null) {
            if (existByLogin(login)) {
                return true;
            }
        }

        if(phoneNumber != null) {
            if (existByPhoneNumber(phoneNumber)) {
                return true;
            }
        }

        return false;
    }

}
