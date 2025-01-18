package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<Transaction> findAll();
    Optional<Transaction> findById(Long id);
    List<Transaction> findByOrderId(Long orderId);
    List<Transaction> findByStatus(String status);
    List<Transaction> findByPaymentMethod(String paymentMethod);
    Transaction save(Transaction transaction);
    void remove(Long id);
}
