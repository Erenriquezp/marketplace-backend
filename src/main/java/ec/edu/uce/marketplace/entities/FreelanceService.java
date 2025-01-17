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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "freelance_services")
public class FreelanceService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(min = 3, max = 100)
    private String name; // Nombre del servicio

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank
    private String description; // Descripción detallada del servicio

    @Column(nullable = false)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price; // Precio del servicio

    @ElementCollection
    @CollectionTable(name = "freelance_service_skills", joinColumns = @JoinColumn(name = "freelance_service_id"))
    @Column(name = "skill", length = 50)
    private List<@Size(max = 50) String> skillsRequired; // Habilidades requeridas para ofrecer el servicio

    @Column(name = "estimated_delivery", nullable = false)
    @NotNull
    private Integer estimatedDelivery; // Tiempo estimado de entrega en días

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user; // Usuario que ofrece el servicio freelance

    @Column(nullable = false)
    private Boolean isActive = true; // Indica si el servicio está activo

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt; // Fecha de creación

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt; // Fecha de última actualización
}
