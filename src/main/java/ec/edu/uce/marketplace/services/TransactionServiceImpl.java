package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.Transaction;
import ec.edu.uce.marketplace.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> findByOrderId(Long orderId) {
        return transactionRepository.findByOrderId(orderId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> findByStatus(String status) {
        return transactionRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> findByPaymentMethod(String paymentMethod) {
        return transactionRepository.findByPaymentMethod(paymentMethod);
    }

    @Transactional
    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        transactionRepository.deleteById(id);
    }
}
