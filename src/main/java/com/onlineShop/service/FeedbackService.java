package com.onlineShop.service;

import com.onlineShop.models.Feedback.Feedback;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FeedbackService {

    boolean save(Feedback feedback);

    Optional<Feedback> getById(String id);

    List<Feedback> getAllFeedbacksForProduct(String productId);

    List<Feedback> getFeedbacksCountForProductAfterDate(String productId, int count, Date date);

    List<Feedback> getAllFeedbacksForUser(String userId);

    Optional<Feedback> getLastFeedbackForProduct(String productId);

    boolean update(Feedback feedback);

    boolean delete(String id);

    boolean deleteAllForProduct(String productId);

    boolean deleteAllForUser(String userId);
}
