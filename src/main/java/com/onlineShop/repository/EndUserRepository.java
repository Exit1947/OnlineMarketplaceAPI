package com.onlineShop.repository;

import com.onlineShop.models.Users.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EndUserRepository extends JpaRepository<EndUser, UUID> {

    Optional<EndUser> findById(String id);

    Optional<EndUser> findByEmail(String email);

    Optional<EndUser> findByLogin(String login);

    Optional<EndUser> findByPhoneNumber(String phoneNumber);

    boolean existsById(String id);

    boolean existsEndUserByEmail(String email);

    boolean existsEndUserByPhoneNumber(String phoneNumber);

    boolean existsEndUserByLogin(String login);

    void deleteById(String id);

}
