package com.onlineShop.repository;

import com.onlineShop.models.Users.Staff.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Optional<Admin> findById(String id);

    Optional<Admin> findByEmail(String email);

    Optional<Admin> findByLogin(String login);

    Optional<Admin> findByPhoneNumber(String phoneNumber);

    boolean existsAdminByEmail(String email);

    boolean existsAdminByPhoneNumber(String phoneNumber);

    boolean existsAdminByLogin(String login);

    void deleteById(String id);

}
