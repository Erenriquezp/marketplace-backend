package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.dtos.FreelanceServiceFilterDTO;
import ec.edu.uce.marketplace.entities.FreelanceService;
import ec.edu.uce.marketplace.entities.Product;
import ec.edu.uce.marketplace.repositories.FreelanceServiceRepository;
import ec.edu.uce.marketplace.specifications.FreelanceServiceSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FreelanceServiceServiceImpl implements FreelanceServiceService {

    private final FreelanceServiceRepository repository;

    public FreelanceServiceServiceImpl(FreelanceServiceRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FreelanceService> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FreelanceService> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public FreelanceService save(FreelanceService freelanceService) {
        return repository.save(freelanceService);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FreelanceService> findWithFilters(FreelanceServiceFilterDTO filters, Pageable pageable) {
        return repository.findAll(FreelanceServiceSpecifications.applyFilters(filters), pageable);
    }

    @Transactional(readOnly = true)
    public Page<FreelanceService> findByFilters(String category, String name, Pageable pageable) {
        if (category != null && !category.isEmpty() && name != null && !name.isEmpty()) {
            return repository.findBySkillsRequiredAndNameContainingIgnoreCase(category, name, pageable);
        } //else if (category != null && !category.isEmpty()) {
           // return repository.findByCategory(category, pageable);
        //}
        else if (name != null && !name.isEmpty()) {
            return repository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            return repository.findAll(pageable); // Si no hay filtros, devuelve todos los productos
        }
    }
}
