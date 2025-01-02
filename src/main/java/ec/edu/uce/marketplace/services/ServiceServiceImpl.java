package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.ServiceEntity;
import ec.edu.uce.marketplace.repositories.ServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceEntity> findAll() {
        return serviceRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ServiceEntity> findById(Long id) {
        return serviceRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceEntity> findByUserId(Long userId) {
        return serviceRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public ServiceEntity save(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        serviceRepository.deleteById(id);
    }
}
