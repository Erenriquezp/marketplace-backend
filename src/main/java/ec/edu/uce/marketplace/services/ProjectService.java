package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.Project;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.repositories.ProjectRepository;
import ec.edu.uce.marketplace.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    /**
     * 📌 Crear un nuevo proyecto para un cliente autenticado.
     */
    public Project createProject(Long clientId, Project project) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        project.setUser(client);
        return projectRepository.save(project);
    }

    /**
     * 📌 Obtener los proyectos creados por un cliente (con paginación).
     */
    @Transactional(readOnly = true)
    public Page<Project> getProjectsByClient(Long clientId, Pageable pageable) {
        return projectRepository.findByUserId(clientId, pageable);
    }

    /**
     * 📌 Obtener todos los proyectos disponibles (para freelancers).
     */
    @Transactional(readOnly = true)
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    /**
     * 📌 Buscar un proyecto por ID.
     */
    @Transactional(readOnly = true)
    public Project findById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    /**
     * 📌 Un cliente autenticado puede actualizar solo sus propios proyectos.
     */
    public Project updateProject(Long clientId, Long projectId, Project projectDetails) {
        Project project = findById(projectId);

        if (!project.getUser().getId().equals(clientId)) {
            throw new RuntimeException("No tienes permisos para modificar este proyecto.");
        }

        project.setTitle(projectDetails.getTitle());
        project.setDescription(projectDetails.getDescription());
        project.setEstimatedBudget(projectDetails.getEstimatedBudget());
        project.setDeadline(projectDetails.getDeadline());

        return projectRepository.save(project);
    }

    /**
     * 📌 Un cliente autenticado puede eliminar solo sus propios proyectos.
     */
    public void deleteProject(Long clientId, Long projectId) {
        Project project = findById(projectId);

        if (!project.getUser().getId().equals(clientId)) {
            throw new RuntimeException("No tienes permisos para eliminar este proyecto.");
        }

        projectRepository.delete(project);
    }
}
