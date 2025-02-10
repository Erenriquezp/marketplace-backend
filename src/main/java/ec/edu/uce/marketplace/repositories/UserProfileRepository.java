package ec.edu.uce.marketplace.repositories;

import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);

    Optional<UserProfile> findByUser(User user);
}
