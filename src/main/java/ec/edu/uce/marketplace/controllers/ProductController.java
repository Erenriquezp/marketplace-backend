package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.entities.Product;
import ec.edu.uce.marketplace.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Obtener todos los productos con paginación (acceso público)
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    // Crear un producto (solo freelancers)
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.status(201).body(productService.save(product));
    }

    // Actualizar un producto existente (solo el propietario del producto)
    @PreAuthorize("hasRole('ROLE_FREELANCER') and #productDetails.user.username == authentication.name")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        return productService.findById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    product.setPrice(productDetails.getPrice());
                    return ResponseEntity.ok(productService.save(product));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un producto (solo administradores)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
