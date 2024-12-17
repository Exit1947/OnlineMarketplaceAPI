package com.onlineShop.repository;

import com.onlineShop.models.Shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findById(long id);

    Optional<Shop> findByStreet(String street);

    List<Shop> findAllByCity(String city);

    void deleteById(long id);

    boolean existsByStreet(String street);

    boolean existsByCity(String city);

}
