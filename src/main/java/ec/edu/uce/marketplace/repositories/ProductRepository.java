package ec.edu.uce.marketplace.repositories;

import ec.edu.uce.marketplace.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByUserId(Long userId, Pageable pageable);
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByCategoryAndNameContainingIgnoreCase(String category, String name, Pageable pageable);
}
