package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.FreelanceService;
import ec.edu.uce.marketplace.repositories.FreelanceServiceRepository;
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
}
