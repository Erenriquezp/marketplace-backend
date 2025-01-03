package ec.edu.uce.marketplace.security;

import ec.edu.uce.marketplace.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderSecurityService {

    private final OrderService orderService;

    /**
     * Verifica si la orden pertenece al usuario autenticado.
     *
     * @param orderId ID de la orden
     * @return true si el usuario autenticado es el propietario de la orden
     */
    public boolean isOrderOwner(Long orderId) {
        var authentication = getAuthentication();
        return orderService.findById(orderId)
                .map(order -> order.getUser().getUsername().equals(authentication.getName()))
                .orElse(false);
    }

    /**
     * Obtiene la autenticación del contexto de seguridad.
     *
     * @return Authentication del usuario actual
     */
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
