package ec.edu.uce.marketplace.repositories;

import ec.edu.uce.marketplace.entities.FreelanceService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreelanceServiceRepository extends JpaRepository<FreelanceService, Long> {
    List<FreelanceService> findByUserId(Long userId);
}
