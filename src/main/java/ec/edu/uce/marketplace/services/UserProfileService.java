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

    public UserProfile createOrUpdateProfile(Long userId, UserProfileDto userProfileDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        System.out.println(userOptional.get().getUpdatedAt());
        User user = userOptional.get();

        UserProfile userProfile = userProfileRepository.findById(userId).orElse(new UserProfile());
        userProfile.setUser(user);
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
