package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.entities.Project;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.services.ProjectService;
import ec.edu.uce.marketplace.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    /**
     * 📌 Un CLIENTE (ROLE_USER) autenticado puede crear un proyecto.
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Project> createProject(@RequestBody Project project, Authentication authentication) {
        // Obtener usuario autenticado
        String username = authentication.getName();
        User client = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Project savedProject = projectService.createProject(client.getId(), project);
        return ResponseEntity.status(201).body(savedProject);
    }

    /**
     * 📌 Un CLIENTE autenticado puede ver solo sus proyectos.
     */
    @GetMapping("/my-projects")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Page<Project>> getProjectsByClient(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort) {

        // Obtener usuario autenticado
        String username = authentication.getName();
        User client = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Project> projects = projectService.getProjectsByClient(client.getId(), pageable);

        return ResponseEntity.ok(projects);
    }

    /**
     * 📌 Obtener todos los proyectos (para freelancers).
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    /**
     * 📌 Un CLIENTE autenticado puede actualizar solo sus propios proyectos.
     */
    @PutMapping("/{projectId}/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestBody Project projectDetails,
            Authentication authentication) {

        // Obtener usuario autenticado
        String username = authentication.getName();
        User client = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar el proyecto y verificar que pertenece al usuario autenticado
        Project project = projectService.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        if (!project.getUser().getId().equals(client.getId())) {
            return ResponseEntity.status(403).body(null); // FORBIDDEN
        }

        // Actualizar los datos del proyecto
        project.setTitle(projectDetails.getTitle());
        project.setDescription(projectDetails.getDescription());
        project.setEstimatedBudget(projectDetails.getEstimatedBudget());
        project.setDeadline(projectDetails.getDeadline());

        Project updatedProject = projectService.saveProject(project);
        return ResponseEntity.ok(updatedProject);
    }

    /**
     * 📌 Un CLIENTE autenticado puede eliminar solo sus propios proyectos.
     */
    @DeleteMapping("/{projectId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId, Authentication authentication) {
        // Obtener usuario autenticado
        String username = authentication.getName();
        User client = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar el proyecto y verificar que pertenece al usuario autenticado
        Project project = projectService.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        if (!project.getUser().getId().equals(client.getId())) {
            return ResponseEntity.status(403).build(); // FORBIDDEN
        }

        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
}
