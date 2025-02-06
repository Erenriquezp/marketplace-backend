package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.ApplicationStatus;
import ec.edu.uce.marketplace.entities.Project;
import ec.edu.uce.marketplace.entities.ProjectApplication;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.repositories.ProjectApplicationRepository;
import ec.edu.uce.marketplace.repositories.ProjectRepository;
import ec.edu.uce.marketplace.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectApplicationService {

    private final ProjectApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectApplicationService(ProjectApplicationRepository applicationRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectApplication applyToProject(Long freelancerId, Long projectId, ProjectApplication application) {
        User freelancer = userRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer no encontrado"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        application.setFreelancer(freelancer);
        application.setProject(project);
        return applicationRepository.save(application);
    }

    // Método para aceptar una postulación
    public ProjectApplication acceptApplication(Long applicationId) {
        ProjectApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        application.setStatus(ApplicationStatus.ACCEPTED);
        return applicationRepository.save(application);
    }

    // Método para rechazar una postulación
    public ProjectApplication rejectApplication(Long applicationId) {
        ProjectApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        application.setStatus(ApplicationStatus.REJECTED);
        return applicationRepository.save(application);
    }

    public List<ProjectApplication> getApplicationsByProject(Long projectId) {
        return applicationRepository.findByProjectId(projectId);
    }

    public List<ProjectApplication> getAcceptedApplications(Long projectId) {
        return applicationRepository.findByProjectIdAndStatus(projectId, ApplicationStatus.ACCEPTED);
    }

    public Optional<ProjectApplication> findById(Long applicationId) {
        return applicationRepository.findById(applicationId);
    }
}
