package ec.edu.uce.marketplace.repositories;

import ec.edu.uce.marketplace.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserId(Long userId);
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

}
