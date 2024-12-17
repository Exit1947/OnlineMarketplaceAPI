package com.onlineShop.repository;

import com.onlineShop.models.Users.Staff.Moderator;
import com.onlineShop.models.Users.Staff.SalesRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SalesRepRepository extends JpaRepository<SalesRep, UUID> {

    Optional<SalesRep> findById(String id);

    Optional<SalesRep> findByEmail(String email);

    Optional<SalesRep> findByLogin(String login);

    Optional<SalesRep> findByPhoneNumber(String phoneNumber);

    List<SalesRep> getAllByAdminId(String id);

    boolean existsSalesRepByEmail(String email);

    boolean existsSalesRepByPhoneNumber(String phoneNumber);

    boolean existsSalesRepByLogin(String login);

    void deleteById(String id);

}
