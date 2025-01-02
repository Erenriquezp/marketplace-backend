package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    List<Product> findByUserId(Long userId);
    Product save(Product product);
    void remove(Long id);
}
