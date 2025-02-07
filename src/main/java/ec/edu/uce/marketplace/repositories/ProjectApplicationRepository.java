package ec.edu.uce.marketplace.repositories;

import ec.edu.uce.marketplace.entities.ProjectApplication;
import ec.edu.uce.marketplace.entities.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {
    Page<ProjectApplication> findByProjectId(Long projectId, Pageable pageable);
    Page<ProjectApplication> findByFreelancerId(Long freelancerId, Pageable pageable);
    Page<ProjectApplication> findByProjectIdAndStatus(Long projectId, ApplicationStatus status, Pageable pageable);
    Optional<ProjectApplication> findByProjectIdAndFreelancerId(Long projectId, Long freelancerId);
}
