package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.dtos.ProductFilterDTO;
import ec.edu.uce.marketplace.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(Long id);
    Product save(Product product);
    void remove(Long id);
    // Nuevo método para buscar productos por categoría
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByFilters(ProductFilterDTO filters, Pageable pageable);
}
