package com.onlineShop.repository;

import com.onlineShop.models.Product.Product;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByTitle(String title);

    boolean existsByTitle(String title);

    @Query(value = "SELECT * FROM product ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<Product> findRandomProduct(@Param("count") int count);

}
