package ec.edu.uce.marketplace.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer; // Comprador

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Producto comprado

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service; // Servicio contratado

    @Column(nullable = false)
    private BigDecimal amount; // Monto de la transacción

    @Column(nullable = false)
    private LocalDateTime transactionDate; // Fecha de la transacción

    // Otros campos y métodos
}