package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.Project;
import ec.edu.uce.marketplace.entities.ProjectApplication;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.entities.ApplicationStatus;
import ec.edu.uce.marketplace.repositories.ProjectApplicationRepository;
import ec.edu.uce.marketplace.repositories.ProjectRepository;
import ec.edu.uce.marketplace.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProjectApplicationService {

    private final ProjectApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectApplicationService(ProjectApplicationRepository applicationRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    /**
     * 📌 Un freelancer puede postularse a un proyecto.
     */
    @Transactional
    public ProjectApplication applyToProject(Long freelancerId, Long projectId, ProjectApplication applicationData) {
        User freelancer = userRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer no encontrado"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        // Verificar si ya está postulado
        if (applicationRepository.findByProjectIdAndFreelancerId(projectId, freelancerId).isPresent()) {
            throw new RuntimeException("Ya estás postulado a este proyecto.");
        }

        ProjectApplication application = new ProjectApplication();
        application.setProject(project);
        application.setFreelancer(freelancer);
        application.setProposal(applicationData.getProposal());
        application.setProposedBudget(applicationData.getProposedBudget());
        application.setStatus(ApplicationStatus.PENDING);

        return applicationRepository.save(application);
    }

    /**
     * 📌 Un cliente puede aceptar/rechazar postulaciones.
     */
    @Transactional
    public ProjectApplication updateApplicationStatus(Long clientId, Long applicationId, ApplicationStatus newStatus) {
        ProjectApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        // Verificar que el usuario sea el propietario del proyecto
        if (!application.getProject().getUser().getId().equals(clientId)) {
            throw new RuntimeException("No tienes permiso para modificar esta postulación.");
        }

        application.setStatus(newStatus);
        return applicationRepository.save(application);
    }

    public Page<ProjectApplication> getApplicationsByProject(Long projectId, Pageable pageable) {
        return applicationRepository.findByProjectId(projectId, pageable);
    }

    public Page<ProjectApplication> getApplicationsByFreelancer(Long id, Pageable pageable) {
        return applicationRepository.findByFreelancerId(id, pageable);
    }

    public ProjectApplication findById(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

    }

    public void deleteApplication(Long id, Long applicationId) {
        ProjectApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));
    }
}
