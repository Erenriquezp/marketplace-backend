package ec.edu.uce.marketplace.repositories;

import ec.edu.uce.marketplace.entities.ProjectApplication;
import ec.edu.uce.marketplace.entities.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {
    List<ProjectApplication> findByProjectId(Long projectId);
    List<ProjectApplication> findByFreelancerId(Long freelancerId);
    List<ProjectApplication> findByProjectIdAndStatus(Long projectId, ApplicationStatus status);
}
