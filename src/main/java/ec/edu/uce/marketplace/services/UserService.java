package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void remove(Long id);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(@NotBlank(message = "El correo no puede estar vacío") @Email(message = "Debe proporcionar un correo electrónico válido") String email);
}
