package ec.edu.uce.marketplace.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // Pedido asociado a esta transacción

    @Column(nullable = false, length = 20)
    @NotBlank
    @Pattern(regexp = "^(PENDING|COMPLETED|FAILED|CANCELLED)$")
    private String status; // Estado de la transacción

    @Column(nullable = false)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount; // Monto de la transacción

    @Column(nullable = false, length = 30)
    @NotBlank
    @Pattern(regexp = "^(CREDIT_CARD|PAYPAL|BANK_TRANSFER|CRYPTO)$")
    private String paymentMethod; // Método de pago utilizado

    @Column(columnDefinition = "TEXT")
    private String transactionDetails; // Detalles adicionales de la transacción

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime transactionDate; // Fecha de creación de la transacción
}
