package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.entities.Review;
import ec.edu.uce.marketplace.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Obtener todas las reseñas
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.findAll();
        return ResponseEntity.ok(reviews);
    }

    // Obtener una reseña por ID
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return reviewService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener reseñas por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        List<Review> reviews = reviewService.findByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    // Obtener reseñas por producto
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable Long productId) {
        List<Review> reviews = reviewService.findByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    // Obtener reseñas por servicio freelance
    @GetMapping("/service/{freelanceServiceId}")
    public ResponseEntity<List<Review>> getReviewsByFreelanceService(@PathVariable Long freelanceServiceId) {
        List<Review> reviews = reviewService.findByFreelanceServiceId(freelanceServiceId);
        return ResponseEntity.ok(reviews);
    }

    // Crear una nueva reseña
    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
        Review savedReview = reviewService.save(review);
        return ResponseEntity.status(201).body(savedReview);
    }

    // Actualizar una reseña existente
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @Valid @RequestBody Review reviewDetails) {
        return reviewService.findById(id)
                .map(review -> {
                    review.setRating(reviewDetails.getRating());
                    review.setComment(reviewDetails.getComment());
                    Review updatedReview = reviewService.save(review);
                    return ResponseEntity.ok(updatedReview);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una reseña
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        if (reviewService.findById(id).isPresent()) {
            reviewService.remove(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
