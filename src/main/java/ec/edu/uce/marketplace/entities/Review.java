package ec.edu.uce.marketplace.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Usuario que dejó la reseña

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Producto relacionado a la reseña (opcional)

    @ManyToOne
    @JoinColumn(name = "freelance_service_id")
    private FreelanceService freelanceService; // Servicio freelance relacionado (opcional)

    @Column(nullable = false)
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating; // Calificación entre 1 y 5

    @Column(columnDefinition = "TEXT")
    @Size(max = 500)
    private String comment; // Comentario opcional (máximo 500 caracteres)

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // Fecha de creación de la reseña
}
