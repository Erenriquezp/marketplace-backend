package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> findAll();
    Optional<Review> findById(Long id);
    List<Review> findByUserId(Long userId);
    List<Review> findByProductId(Long productId);
    List<Review> findByFreelanceServiceId(Long freelanceServiceId);
    Review save(Review review);
    void remove(Long id);
}
