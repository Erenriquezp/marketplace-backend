package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.dtos.ProductFilterDTO;
import ec.edu.uce.marketplace.entities.Product;
import ec.edu.uce.marketplace.repositories.ProductRepository;
import ec.edu.uce.marketplace.specifications.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findByUserId(Long userId, Pageable pageable) {
        return productRepository.findByUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> findByCategory(String category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Product> findByFilters(String category, String name, Pageable pageable) {
        if (category != null && !category.isEmpty() && name != null && !name.isEmpty()) {
            return productRepository.findByCategoryAndNameContainingIgnoreCase(category, name, pageable);
        } else if (category != null && !category.isEmpty()) {
            return productRepository.findByCategory(category, pageable);
        } else if (name != null && !name.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            return productRepository.findAll(pageable); // Si no hay filtros, devuelve todos los productos
        }
    }
}
