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
     * 📌 Un CLIENTE (`ROLE_USER`) autenticado puede crear un proyecto.
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Project> createProject(@RequestBody Project project, Authentication authentication) {
        User client = getAuthenticatedUser(authentication);
        Project savedProject = projectService.createProject(client.getId(), project);
        return ResponseEntity.status(201).body(savedProject);
    }

    /**
     * 📌 Un CLIENTE autenticado puede ver solo sus propios proyectos.
     */
    @GetMapping("/my-projects")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Page<Project>> getProjectsByClient(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort) {

        User client = getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Project> projects = projectService.getProjectsByClient(client.getId(), pageable);

        return ResponseEntity.ok(projects);
    }

    /**
     * 📌 Obtener todos los proyectos disponibles (para freelancers).
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ResponseEntity<Page<Project>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Project> projects = projectService.getAllProjects(pageable);

        return ResponseEntity.ok(projects);
    }

    /**
     * 📌 Un CLIENTE autenticado puede actualizar solo sus propios proyectos.
     */
    @PutMapping("/{projectId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestBody Project projectDetails,
            Authentication authentication) {

        User client = getAuthenticatedUser(authentication);
        Project updatedProject = projectService.updateProject(client.getId(), projectId, projectDetails);

        return ResponseEntity.ok(updatedProject);
    }

    /**
     * 📌 Un CLIENTE autenticado puede eliminar solo sus propios proyectos.
     */
    @DeleteMapping("/{projectId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId, Authentication authentication) {
        User client = getAuthenticatedUser(authentication);
        projectService.deleteProject(client.getId(), projectId);

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
