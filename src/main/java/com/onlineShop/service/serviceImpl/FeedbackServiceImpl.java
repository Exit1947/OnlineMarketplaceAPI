package com.onlineShop.service.serviceImpl;

import com.onlineShop.models.Feedback.Feedback;
import com.onlineShop.repository.FeedbackRepository;
import com.onlineShop.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(final FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public boolean save(Feedback feedback) {
        feedback.setId(UUID.randomUUID().toString());
        feedbackRepository.save(feedback);
        return true;
    }

    @Override
    public Optional<Feedback> getById(String id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public List<Feedback> getAllFeedbacksForProduct(String productId) {
        return feedbackRepository.findAllByProductId(productId);
    }

    @Override
    public List<Feedback> getFeedbacksCountForProductAfterDate(String productId, int count, Date date) {
        return getAllFeedbacksForUser(productId).stream()
                .filter(feedback -> feedback.getDatePublication().before(date))
                .limit(count)
                .toList();
    }

    @Override
    public List<Feedback> getAllFeedbacksForUser(String userId) {
        return feedbackRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Feedback> getLastFeedbackForProduct(String productId) {
        return getAllFeedbacksForProduct(productId).stream()
                .max(Comparator.comparing(Feedback::getDatePublication));
    }

    @Override
    public boolean update(Feedback feedback) {
        Optional<Feedback> existingFeedback = feedbackRepository.findById(feedback.getId());
        if(existingFeedback.isPresent()) {
            feedback.setId(existingFeedback.get().getId());
            feedbackRepository.save(feedback);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        if(feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllForProduct(String productId) {
        getAllFeedbacksForProduct(productId).
                forEach(feedback -> feedbackRepository.deleteById(feedback.getId()));
        return true;
    }

    @Override
    public boolean deleteAllForUser(String userId) {
        getAllFeedbacksForUser(userId)
                .forEach(feedback -> feedbackRepository.deleteById(feedback.getId()));
        return true;
    }

}
