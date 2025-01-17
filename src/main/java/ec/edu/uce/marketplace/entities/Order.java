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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Pattern(regexp = "^(FREELANCE|PRODUCT)$", message = "El tipo debe ser 'FREELANCE' o 'PRODUCT'")
    private String type; // Tipo de orden: Freelance o Producto

    @Column(nullable = false)
    @NotBlank
    @Pattern(regexp = "^(PENDING|COMPLETED|CANCELLED)$", message = "El estado debe ser 'PENDING', 'COMPLETED' o 'CANCELLED'")
    private String status; // Estado de la orden

    @Column(name = "order_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime orderDate; // Fecha en la que se creó la orden

    @Column(name = "completion_date")
    private LocalDateTime completionDate; // Fecha en la que se completó la orden

    @Column(nullable = false)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount; // Monto total de la orden

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user; // Usuario que realizó la orden

    @ManyToOne
    @JoinColumn(name = "freelance_service_id")
    private FreelanceService freelanceService; // Servicio freelance relacionado (opcional)

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Producto relacionado (opcional)

    @Column(nullable = true, columnDefinition = "TEXT")
    private String notes; // Notas adicionales sobre la orden

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt; // Fecha de última actualización
}
