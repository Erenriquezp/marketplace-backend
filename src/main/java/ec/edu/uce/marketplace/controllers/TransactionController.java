package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.entities.Transaction;
import ec.edu.uce.marketplace.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Obtener todas las transacciones
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }

    // Obtener una transacción por ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener transacciones por pedido
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Transaction>> getTransactionsByOrder(@PathVariable Long orderId) {
        List<Transaction> transactions = transactionService.findByOrderId(orderId);
        return ResponseEntity.ok(transactions);
    }

    // Obtener transacciones por estado
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Transaction>> getTransactionsByStatus(@PathVariable String status) {
        List<Transaction> transactions = transactionService.findByStatus(status);
        return ResponseEntity.ok(transactions);
    }

    // Obtener transacciones por método de pago
    @GetMapping("/payment-method/{paymentMethod}")
    public ResponseEntity<List<Transaction>> getTransactionsByPaymentMethod(@PathVariable String paymentMethod) {
        List<Transaction> transactions = transactionService.findByPaymentMethod(paymentMethod);
        return ResponseEntity.ok(transactions);
    }

    // Crear una nueva transacción
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.save(transaction);
        return ResponseEntity.status(201).body(savedTransaction);
    }

    // Actualizar una transacción existente
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @Valid @RequestBody Transaction transactionDetails) {
        return transactionService.findById(id)
                .map(transaction -> {
                    transaction.setStatus(transactionDetails.getStatus());
                    transaction.setAmount(transactionDetails.getAmount());
                    transaction.setPaymentMethod(transactionDetails.getPaymentMethod());
                    transaction.setTransactionDetails(transactionDetails.getTransactionDetails());
                    Transaction updatedTransaction = transactionService.save(transaction);
                    return ResponseEntity.ok(updatedTransaction);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una transacción
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        if (transactionService.findById(id).isPresent()) {
            transactionService.remove(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
