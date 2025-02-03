package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.entities.FreelanceService;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.services.FreelanceServiceService;
import ec.edu.uce.marketplace.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/freelancer-services")
public class FreelanceServiceController {

    private final FreelanceServiceService freelancerServiceService;
    private final UserService userService;

    public FreelanceServiceController(FreelanceServiceService freelancerServiceService, UserService userService) {
        this.freelancerServiceService = freelancerServiceService;
        this.userService = userService;
    }

    // Obtener todos los servicios con paginación (acceso público)
    @GetMapping
    public ResponseEntity<Page<FreelanceService>> getAllServices(Pageable pageable) {
        return ResponseEntity.ok(freelancerServiceService.findAll(pageable));
    }

    // Obtener un servicio por ID (acceso público)
    @GetMapping("/{id}")
    public ResponseEntity<FreelanceService> getServiceById(@PathVariable Long id) {
        return freelancerServiceService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo servicio (solo freelancers)
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @PostMapping
    public ResponseEntity<FreelanceService> createService(@Valid @RequestBody FreelanceService freelancerService, Authentication authentication) {
        // Obtener el usuario autenticado
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Asociar el servicio con el usuario autenticado
        freelancerService.setUser (user);
        FreelanceService savedService = freelancerServiceService.save(freelancerService);

        return ResponseEntity.status(201).body(savedService);
    }

    // Actualizar un servicio existente (solo el propietario del servicio)
//    @PreAuthorize("hasRole('ROLE_FREELANCER') and #serviceDetails.user.username == authentication.name")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @PutMapping("/{id}")
    public ResponseEntity<FreelanceService> updateService(
            @PathVariable Long id, @Valid @RequestBody FreelanceService serviceDetails) {

        return freelancerServiceService.findById(id)
                .map(service -> {
                    service.setName(serviceDetails.getName());
                    service.setDescription(serviceDetails.getDescription());
                    service.setPrice(serviceDetails.getPrice());
                    return ResponseEntity.ok(freelancerServiceService.save(service));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un servicio (solo administradores)
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        freelancerServiceService.remove(id);
        return ResponseEntity.noContent().build();
    }
}