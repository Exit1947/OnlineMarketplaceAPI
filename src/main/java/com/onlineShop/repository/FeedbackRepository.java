package com.onlineShop.repository;

import com.onlineShop.models.Feedback.Feedback;
import com.onlineShop.models.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {

    List<Feedback> findAllByUserId(String userId);

    List<Feedback> findAllByProductId(String productId);

}
