package ec.edu.uce.marketplace.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank
    private String description;

    @Column(nullable = false)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @Column(nullable = false, length = 50)
    @NotBlank
    @Size(min = 3, max = 50)
    private String category; // Nueva columna para categorizar el producto

    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag", length = 30)
    private Set<@Size(max = 30) String> tags; // Nuevos tags asociados al producto

    @Column(name = "file_url")
    private String fileUrl; // URL opcional para archivos relacionados con el producto

    @Column(nullable = false)
    private Boolean isActive = true; // Indica si el producto está activo

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt; // Fecha de creación

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt; // Fecha de última actualización

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Indica que esta es la parte "no gestionada" de la relación
    private User user; // Usuario que ofrece este producto
}
