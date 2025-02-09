package ec.edu.uce.marketplace.repositories;

import ec.edu.uce.marketplace.entities.FreelanceService;
import ec.edu.uce.marketplace.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreelanceServiceRepository extends JpaRepository<FreelanceService, Long>, JpaSpecificationExecutor<FreelanceService> {
    Page<FreelanceService> findByUserId(Long userId, Pageable pageable);
    // Buscar por nombre (coincidencias parciales)
    Page<FreelanceService> findByNameContainingIgnoreCase(String name, Pageable pageable);
    // Buscar por nombre y categoría combinados
    Page<FreelanceService> findBySkillsRequiredAndNameContainingIgnoreCase(List<String> skillsRequired, @NotBlank @Size(min = 3, max = 100) String name, Pageable pageable);

    Page<FreelanceService> findByName(String name, Pageable pageable);
}
