package com.onlineShop.service.serviceImpl;

import com.onlineShop.models.Users.EndUserEntities.LikedProduct;
import com.onlineShop.repository.LikedProductRepository;
import com.onlineShop.service.LikedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikedProductServiceImpl implements LikedProductService {

    private final LikedProductRepository likedProductRepository;

    @Autowired
    public LikedProductServiceImpl(LikedProductRepository likedProductRepository) {
        this.likedProductRepository = likedProductRepository;
    }

    @Override
    public boolean save(LikedProduct likedProduct) {
        if(!likedProductRepository.existsByUserIdAndProductId(likedProduct.getUser().getId(), likedProduct.getProduct().getId())) {
            likedProductRepository.save(likedProduct);
            return true;
        }
        return false;
    }

    @Override
    public Optional<LikedProduct> getLikedProductById(String id) {
        return likedProductRepository.findById(id);
    }

    @Override
    public List<LikedProduct> getAllLikedProductsByUserId(String userId) {
        return likedProductRepository.findAllByUserId(userId);
    }

    @Override
    public List<LikedProduct> getAllLikedProductsByProductId(String productId) {
        return likedProductRepository.findAllByProductId(productId);
    }

    @Override
    public boolean update(LikedProduct likedProduct) {
        Optional<LikedProduct> existingLikedProduct = likedProductRepository.findById(likedProduct.getId());
        if(existingLikedProduct.isPresent()) {
            likedProduct.setId(existingLikedProduct.get().getId());
            likedProductRepository.save(likedProduct);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        if(likedProductRepository.existsById(id)) {
            likedProductRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAll(List<LikedProduct> likedProducts) {
        likedProducts
                .forEach(likedProduct -> delete(likedProduct.getId()));
        return true;
    }

}
