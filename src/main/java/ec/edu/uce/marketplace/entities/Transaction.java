package ec.edu.uce.marketplace.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
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
    private FreelanceService service; // Servicio contratado

    @Column(nullable = false)
    private BigDecimal amount; // Monto de la transacción

    @Column(nullable = false)
    private LocalDateTime transactionDate; // Fecha de la transacción

    // Constructor sin argumentos
    public Transaction() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public FreelanceService getService() {
        return service;
    }

    public void setService(FreelanceService service) {
        this.service = service;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}