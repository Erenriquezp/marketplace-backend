package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.dtos.FreelanceServiceFilterDTO;
import ec.edu.uce.marketplace.entities.FreelanceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FreelanceServiceService {
    Page<FreelanceService> findAll(Pageable pageable);
    Optional<FreelanceService> findById(Long id);
    FreelanceService save(FreelanceService freelanceService);
    void remove(Long id);
    Page<FreelanceService> findWithFilters(FreelanceServiceFilterDTO filters, Pageable pageable);
}
