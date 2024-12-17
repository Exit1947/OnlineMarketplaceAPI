package com.onlineShop.service;

import com.onlineShop.models.Shop.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopService {

    void save(Shop shop);

    Optional<Shop> getById(long id);

    Optional<Shop> getByStreet(String street);

    List<Shop> getAllByCityName(String cityName);

    void delete(long id);

    boolean existById(long id);

    boolean existByStreet(String street);

    boolean existByCityName(String cityName);

}
