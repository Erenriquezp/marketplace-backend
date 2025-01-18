package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.Review;
import ec.edu.uce.marketplace.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Review> findByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Review> findByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Review> findByFreelanceServiceId(Long freelanceServiceId) {
        return reviewRepository.findByFreelanceServiceId(freelanceServiceId);
    }

    @Transactional
    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        reviewRepository.deleteById(id);
    }
}
