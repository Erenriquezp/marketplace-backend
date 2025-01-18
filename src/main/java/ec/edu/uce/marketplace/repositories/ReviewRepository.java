package ec.edu.uce.marketplace.repositories;

import ec.edu.uce.marketplace.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Obtener todas las reseñas de un usuario
    List<Review> findByUserId(Long userId);

    // Obtener todas las reseñas de un producto
    List<Review> findByProductId(Long productId);

    // Obtener todas las reseñas de un servicio freelance
    List<Review> findByFreelanceServiceId(Long freelanceServiceId);
}
