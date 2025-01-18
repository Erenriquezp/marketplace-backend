package ec.edu.uce.marketplace.repositories;

import ec.edu.uce.marketplace.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByEmail(@NotBlank(message = "El correo no puede estar vacío") @Email(message = "Debe proporcionar un correo electrónico válido") String email);
}
