package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.entities.FreelanceService;
import ec.edu.uce.marketplace.services.FreelanceServiceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/freelancer-services")
public class FreelanceServiceController {

    private final FreelanceServiceService freelancerServiceService;

    public FreelanceServiceController(FreelanceServiceService freelancerServiceService) {
        this.freelancerServiceService = freelancerServiceService;
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
    public ResponseEntity<FreelanceService> createService(@Valid @RequestBody FreelanceService freelancerService) {
        return ResponseEntity.status(201).body(freelancerServiceService.save(freelancerService));
    }

    // Actualizar un servicio existente (solo el propietario del servicio)
//    @PreAuthorize("hasRole('ROLE_FREELANCER') and #serviceDetails.user.username == authentication.name")
//    @PutMapping("/{id}")
//    public ResponseEntity<FreelanceService> updateService(
//            @PathVariable Long id, @Valid @RequestBody FreelanceService serviceDetails) {
//
//        return freelancerServiceService.findById(id)
//                .map(service -> {
//                    service.setName(serviceDetails.getName());
//                    service.setDescription(serviceDetails.getDescription());
//                    service.setPrice(serviceDetails.getPrice());
//                    return ResponseEntity.ok(freelancerServiceService.save(service));
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    // Eliminar un servicio (solo administradores)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        freelancerServiceService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
