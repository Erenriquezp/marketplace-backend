package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.entities.Project;
import ec.edu.uce.marketplace.entities.ProjectApplication;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.entities.ApplicationStatus;
import ec.edu.uce.marketplace.services.ProjectApplicationService;
import ec.edu.uce.marketplace.services.ProjectService;
import ec.edu.uce.marketplace.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ProjectApplicationController {

    private final ProjectApplicationService applicationService;
    private final ProjectService projectService;
    private final UserService userService;

    public ProjectApplicationController(ProjectApplicationService applicationService, ProjectService projectService, UserService userService) {
        this.applicationService = applicationService;
        this.projectService = projectService;
        this.userService = userService;
    }

    /**
     * 📌 Un freelancer autenticado se postula a un proyecto.
     */
    @PostMapping("/apply/{projectId}")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ResponseEntity<ProjectApplication> applyToProject(
            @PathVariable Long projectId,
            @RequestBody ProjectApplication application,
            Authentication authentication) {

        // Obtener usuario autenticado
        String username = authentication.getName();
        User freelancer = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Guardar postulación
        ProjectApplication savedApplication = applicationService.applyToProject(freelancer.getId(), projectId, application);
        return ResponseEntity.status(201).body(savedApplication);
    }

    /**
     * 📌 Un cliente autenticado acepta una postulación.
     */
    @PutMapping("/{applicationId}/accept")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ProjectApplication> acceptApplication(@PathVariable Long applicationId, Authentication authentication) {
        String username = authentication.getName();
        User client = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ProjectApplication application = applicationService.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        // Validar que el usuario autenticado es el dueño del proyecto
        if (!application.getProject().getClient().getId().equals(client.getId())) {
            return ResponseEntity.status(403).body(null); // FORBIDDEN
        }

        ProjectApplication updatedApplication = applicationService.acceptApplication(applicationId);
        return ResponseEntity.ok(updatedApplication);
    }

    /**
     * 📌 Un cliente autenticado rechaza una postulación.
     */
    @PutMapping("/{applicationId}/reject")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ProjectApplication> rejectApplication(@PathVariable Long applicationId, Authentication authentication) {
        String username = authentication.getName();
        User client = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ProjectApplication application = applicationService.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        // Validar que el usuario autenticado es el dueño del proyecto
        if (!application.getProject().getClient().getId().equals(client.getId())) {
            return ResponseEntity.status(403).body(null); // FORBIDDEN
        }

        ProjectApplication updatedApplication = applicationService.rejectApplication(applicationId);
        return ResponseEntity.ok(updatedApplication);
    }

    /**
     * 📌 Obtener postulaciones de un proyecto.
     */
    @GetMapping("/by-project/{projectId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_FREELANCER')")
    public ResponseEntity<List<ProjectApplication>> getApplicationsByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(applicationService.getApplicationsByProject(projectId));
    }

    /**
     * 📌 Obtener postulaciones aceptadas de un proyecto.
     */
    @GetMapping("/by-project/{projectId}/accepted")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_FREELANCER')")
    public ResponseEntity<List<ProjectApplication>> getAcceptedApplications(@PathVariable Long projectId) {
        return ResponseEntity.ok(applicationService.getAcceptedApplications(projectId));
    }
}
