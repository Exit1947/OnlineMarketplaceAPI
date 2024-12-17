package com.onlineShop.repository;

import com.onlineShop.models.Users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    boolean existsUserEntitiesByEmail(String email);

    boolean existsUserEntitiesByPhoneNumber(String phoneNumber);

    boolean existsUserEntitiesByLogin(String login);

    void deleteById(String id);

}
