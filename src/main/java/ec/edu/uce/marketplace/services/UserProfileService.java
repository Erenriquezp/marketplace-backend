package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.dtos.UserProfileDto;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.entities.UserProfile;
import ec.edu.uce.marketplace.repositories.UserProfileRepository;
import ec.edu.uce.marketplace.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserProfile createOrUpdateProfile(Long userId, UserProfileDto userProfileDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = userOptional.get();

        // Buscar el perfil del usuario, si no existe, crear uno nuevo
        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserProfile newProfile = new UserProfile();
                    newProfile.setUser(user); // Relacionar con el usuario
                    return newProfile;
                });

        userProfile.setCountry(userProfileDto.getCountry());
        userProfile.setCountryFlagUrl(userProfileDto.getCountryFlagUrl());
        userProfile.setPresentation(userProfileDto.getPresentation());
        userProfile.setLanguage(userProfileDto.getLanguage());
        userProfile.setDescription(userProfileDto.getDescription());
        userProfile.setExperience(userProfileDto.getExperience());
        userProfile.setReference(userProfileDto.getReference());
        userProfile.setEducation(userProfileDto.getEducation());
        userProfile.setSkills(userProfileDto.getSkills());
        userProfile.setCertifications(userProfileDto.getCertifications());
        userProfile.setQualifications(userProfileDto.getQualifications());
        userProfile.setPublications(userProfileDto.getPublications());
        userProfile.setSocialLinks(userProfileDto.getSocialLinks());

        return userProfileRepository.save(userProfile);
    }

    // Obtener perfil por ID de usuario
    @Transactional(readOnly = true)
    public UserProfile getProfileByUserId(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado para el usuario con ID: " + userId));
    }
}
