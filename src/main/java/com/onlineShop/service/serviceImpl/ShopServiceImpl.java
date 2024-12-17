package com.onlineShop.service.serviceImpl;

import com.onlineShop.models.Shop.Shop;
import com.onlineShop.repository.ShopRepository;
import com.onlineShop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public void save(Shop shop) {
        shopRepository.save(shop);
    }

    @Override
    public Optional<Shop> getById(long id) {
        return shopRepository.findById(id);
    }

    @Override
    public Optional<Shop> getByStreet(String street) {
        return shopRepository.findByStreet(street);
    }

    @Override
    public List<Shop> getAllByCityName(String cityName) {
        return shopRepository.findAllByCity(cityName);
    }

    @Override
    public void delete(long id) {
        shopRepository.deleteById(id);
    }

    @Override
    public boolean existById(long id) {
        return shopRepository.existsById(id);
    }

    @Override
    public boolean existByStreet(String street) {
        return shopRepository.existsByStreet(street);
    }

    @Override
    public boolean existByCityName(String cityName) {
        return shopRepository.existsByCity(cityName);
    }

}
