package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.entities.Project;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.services.ProjectService;
import ec.edu.uce.marketplace.services.UserService;
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
    public ResponseEntity<List<Project>> getClientProjects(Authentication authentication) {
        // Obtener usuario autenticado
        String username = authentication.getName();
        User client = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Project> projects = projectService.getProjectsByClient(client.getId());
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
}
