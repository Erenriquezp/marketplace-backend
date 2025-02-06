package ec.edu.uce.marketplace.repositories;

import ec.edu.uce.marketplace.entities.FreelanceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreelanceServiceRepository extends JpaRepository<FreelanceService, Long>, JpaSpecificationExecutor<FreelanceService> {
    List<FreelanceService> findByUserId(Long userId);
    Page<FreelanceService> findAll(Specification<FreelanceService> spec, Pageable pageable);
    // Buscar por nombre (coincidencias parciales)
    Page<FreelanceService> findByNameContainingIgnoreCase(String name, Pageable pageable);
    // Buscar por nombre y categoría combinados
    Page<FreelanceService> findBySkillsRequiredAndNameContainingIgnoreCase(String category, String name, Pageable pageable);

}
