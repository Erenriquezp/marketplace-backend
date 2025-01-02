package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.ServiceEntity;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    List<ServiceEntity> findAll();
    Optional<ServiceEntity> findById(Long id);
    List<ServiceEntity> findByUserId(Long userId);
    ServiceEntity save(ServiceEntity service);
    void remove(Long id);
}
