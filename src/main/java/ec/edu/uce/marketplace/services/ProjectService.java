package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.Project;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.repositories.ProjectRepository;
import ec.edu.uce.marketplace.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project createProject(Long clientId, Project project) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        project.setClient(client);
        return projectRepository.save(project);
    }

    public List<Project> getProjectsByClient(Long clientId) {
        return projectRepository.findByClientId(clientId);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
