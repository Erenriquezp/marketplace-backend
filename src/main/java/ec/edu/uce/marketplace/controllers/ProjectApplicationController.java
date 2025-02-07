package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.entities.ProjectApplication;
import ec.edu.uce.marketplace.entities.ApplicationStatus;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.services.ProjectApplicationService;
import ec.edu.uce.marketplace.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class ProjectApplicationController {

    private final ProjectApplicationService applicationService;
    private final UserService userService;

    public ProjectApplicationController(ProjectApplicationService applicationService, UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }

    /**
     * 📌 Freelancer puede postularse a un proyecto.
     */
    @PostMapping("/{projectId}/apply")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ResponseEntity<ProjectApplication> applyToProject(
            @PathVariable Long projectId,
            @RequestBody ProjectApplication applicationData,
            Authentication authentication) {

        User freelancer = getAuthenticatedUser(authentication);
        ProjectApplication savedApplication = applicationService.applyToProject(freelancer.getId(), projectId, applicationData);

        return ResponseEntity.status(201).body(savedApplication);
    }

    /**
     * 📌 Cliente puede aceptar o rechazar postulaciones.
     */
    @PutMapping("/{applicationId}/update-status")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ProjectApplication> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam ApplicationStatus status,
            Authentication authentication) {

        User client = getAuthenticatedUser(authentication);
        ProjectApplication updatedApplication = applicationService.updateApplicationStatus(client.getId(), applicationId, status);

        return ResponseEntity.ok(updatedApplication);
    }

    /**
     * 📌 Cliente puede ver todas las postulaciones a su proyecto.
     */
    @GetMapping("/by-project/{projectId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Page<ProjectApplication>> getApplicationsByProject(
            @PathVariable Long projectId,
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        User client = getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectApplication> applications = applicationService.getApplicationsByProject(projectId, pageable);

        return ResponseEntity.ok(applications);
    }

    /**
     * 📌 Freelancer puede ver todas sus postulaciones.
     */
    @GetMapping("/by-freelancer")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ResponseEntity<Page<ProjectApplication>> getApplicationsByFreelancer(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        User freelancer = getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectApplication> applications = applicationService.getApplicationsByFreelancer(freelancer.getId(), pageable);

        return ResponseEntity.ok(applications);
    }

    /**
     * 📌 Obtener una postulación por su ID.
     */
    @GetMapping("/{applicationId}")
    public ResponseEntity<ProjectApplication> getApplicationById(@PathVariable Long applicationId) {
        return ResponseEntity.ok(applicationService.findById(applicationId));
    }

    /**
     * 📌 Cliente puede eliminar una postulación si aún no ha sido aceptada.
     */
    @DeleteMapping("/{applicationId}/delete")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long applicationId, Authentication authentication) {
        User client = getAuthenticatedUser(authentication);
        applicationService.deleteApplication(client.getId(), applicationId);

        return ResponseEntity.noContent().build();
    }

    /**
     * 📌 Método reutilizable para obtener el usuario autenticado.
     */
    private User getAuthenticatedUser(Authentication authentication) {
        return userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
