package com.onlineShop.service.serviceImpl;

import com.onlineShop.models.Product.DiscountProduct;
import com.onlineShop.repository.DiscountProductRepository;
import com.onlineShop.service.DiscountProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountProductServiceImpl implements DiscountProductService {

    private final DiscountProductRepository discountProductRepository;

    @Autowired
    public DiscountProductServiceImpl(final DiscountProductRepository discountProductRepository){
        this.discountProductRepository = discountProductRepository;
    }

    @Override
    @Transactional
    public boolean save(DiscountProduct discountProduct) {
        Optional<DiscountProduct> existedDiscountProduct = discountProductRepository.findByProductId(discountProduct.getProduct().getId());
        if(existedDiscountProduct.isEmpty()){
            discountProductRepository.save(discountProduct);
            return true;
        }
        return false;
    }

    @Override
    public Optional<DiscountProduct> findById(long id) {
        return discountProductRepository.findById(id);
    }

    @Override
    public Optional<DiscountProduct> findByProductId(String productId) {
        return discountProductRepository.findByProductId(productId);
    }

    @Override
    public boolean update(DiscountProduct discountProduct) {
        Optional<DiscountProduct> existedDiscountProduct = discountProductRepository.findByProductId(discountProduct.getProduct().getId());
        if(existedDiscountProduct.isPresent()){
            DiscountProduct existingDiscount = existedDiscountProduct.get();
            discountProduct.setId(discountProduct.getId());

            discountProductRepository.save(discountProduct);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        Optional<DiscountProduct> existedDiscountProduct = discountProductRepository.findById(id);
        if(existedDiscountProduct.isPresent()){
            discountProductRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAll(List<DiscountProduct> productList) {
        for(DiscountProduct discountProduct : productList){
            delete(discountProduct.getId());
            return true;
        }
        return false;
    }

}
