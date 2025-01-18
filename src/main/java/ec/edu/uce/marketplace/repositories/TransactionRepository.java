package ec.edu.uce.marketplace.repositories;

import ec.edu.uce.marketplace.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Obtener todas las transacciones asociadas a un pedido específico
    List<Transaction> findByOrderId(Long orderId);

    // Obtener todas las transacciones por estado
    List<Transaction> findByStatus(String status);

    // Obtener todas las transacciones por método de pago
    List<Transaction> findByPaymentMethod(String paymentMethod);
}
