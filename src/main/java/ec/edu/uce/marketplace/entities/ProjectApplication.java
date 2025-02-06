package ec.edu.uce.marketplace.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_applications")
public class ProjectApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonBackReference
    private Project project; // Proyecto al que se postula

    @ManyToOne
    @JoinColumn(name = "freelancer_id", nullable = false)
    private User freelancer; // Freelancer que se postula

    @Column(nullable = false)
    @NotBlank
    private String proposal; // Propuesta del freelancer

    @Column(nullable = false)
    @NotNull
    private BigDecimal proposedBudget; // Presupuesto propuesto por el freelancer

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private ApplicationStatus status = ApplicationStatus.PENDING; // Estado de la postulación

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime appliedAt;
}
