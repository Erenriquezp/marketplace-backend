package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.entities.Order;
import ec.edu.uce.marketplace.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Obtener todas las órdenes con paginación (Solo administradores)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<Order>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.findAll(pageable));
    }

    // Obtener órdenes de un usuario (Solo el usuario o administradores)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == authentication.principal.id")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Order>> getOrdersByUserId(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(orderService.findByUserId(userId, pageable));
    }

    // Obtener una orden por ID (Solo el propietario o administradores)
    @PreAuthorize("hasRole('ROLE_ADMIN') or @orderSecurityService.isOrderOwner(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva orden (Solo usuarios autenticados)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        return ResponseEntity.status(201).body(orderService.save(order));
    }

    // Actualizar el estado de una orden (Solo administradores)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id, @RequestBody Order orderDetails) {

        return orderService.findById(id)
                .map(order -> {
                    order.setStatus(orderDetails.getStatus());
                    return ResponseEntity.ok(orderService.save(order));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una orden (Solo administradores)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
