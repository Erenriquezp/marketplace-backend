package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    List<Order> findByUserId(Long userId);
    Order save(Order order);
    void remove(Long id);
}
