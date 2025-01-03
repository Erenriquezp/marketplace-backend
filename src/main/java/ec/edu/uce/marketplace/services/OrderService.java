package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderService {
    Page<Order> findAll(Pageable pageable);
    Page<Order> findByUserId(Long userId, Pageable pageable);
    Optional<Order> findById(Long id);
    Order save(Order order);
    void remove(Long id);
}
