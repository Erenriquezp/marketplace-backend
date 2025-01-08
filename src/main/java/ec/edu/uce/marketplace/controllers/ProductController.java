package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.entities.Product;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.services.ProductService;
import ec.edu.uce.marketplace.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    // Obtener todos los productos con paginación (acceso público)
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, Authentication authentication) {
        // Obtener el usuario autenticado
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        System.out.println("user = " + user);
        // Asociar el producto con el usuario autenticado
        product.setUser(user);
        Product savedProduct = productService.save(product);

        return ResponseEntity.status(201).body(savedProduct);
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
