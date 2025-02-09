package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.dtos.FreelanceServiceFilterDTO;
import ec.edu.uce.marketplace.entities.FreelanceService;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.services.FreelanceServiceService;
import ec.edu.uce.marketplace.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    /**
     * 📌 Obtener todos los servicios con paginación (acceso público).
     */
    @GetMapping
    public ResponseEntity<Page<FreelanceService>> getAllServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(freelancerServiceService.findAll(pageable));
    }

    /**
     * 📌 Obtener los servicios de un usuario específico.
     */
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Page<FreelanceService>> getServicesByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(freelancerServiceService.findByUserId(userId, pageable));
    }

    /**
     * 📌 Obtener un servicio por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FreelanceService> getServiceById(@PathVariable Long id) {
        return freelancerServiceService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 📌 Crear un nuevo servicio (solo freelancers autenticados).
     */
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @PostMapping
    public ResponseEntity<FreelanceService> createService(
            @Valid @RequestBody FreelanceService freelancerService, Authentication authentication) {

        User user = getAuthenticatedUser(authentication);
        freelancerService.setUser(user);
        FreelanceService savedService = freelancerServiceService.save(freelancerService);

        return ResponseEntity.status(201).body(savedService);
    }

    /**
     * 📌 Actualizar un servicio existente (solo el propietario del servicio).
     */
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @PutMapping("/{id}")
    public ResponseEntity<FreelanceService> updateService(
            @PathVariable Long id, @Valid @RequestBody FreelanceService serviceDetails, Authentication authentication) {

        User user = getAuthenticatedUser(authentication);

        return freelancerServiceService.findById(id)
                .map(service -> {

                    service.setName(serviceDetails.getName());
                    service.setDescription(serviceDetails.getDescription());
                    service.setPrice(serviceDetails.getPrice());
                    service.setSkillsRequired(serviceDetails.getSkillsRequired());
                    return ResponseEntity.ok(freelancerServiceService.save(service));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 📌 Eliminar un servicio (solo el propietario del servicio).
     */
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id, Authentication authentication) {
        freelancerServiceService.remove(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 📌 Buscar servicios con filtros (categoría, nombre, habilidades, etc.).
     */
    @PostMapping("/search")
    public ResponseEntity<Page<FreelanceService>> searchFreelanceServices(
            @RequestBody FreelanceServiceFilterDTO filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(freelancerServiceService.findWithFilters(filters, pageable));
    }

    /**
     * 📌 Buscar servicios por categoría o nombre.
     */
    @GetMapping("/search")
    public ResponseEntity<Page<FreelanceService>> searchServicesByCategoryOrName(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(freelancerServiceService.findByName(name, pageable));
    }

    /**
     * 📌 Método privado para obtener el usuario autenticado.
     */
    private User getAuthenticatedUser(Authentication authentication) {
        return userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
