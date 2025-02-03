package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.dtos.UserProfileDto;
import ec.edu.uce.marketplace.entities.UserProfile;
import ec.edu.uce.marketplace.services.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    // Crear o actualizar perfil
    @PostMapping("/{userId}")
    public ResponseEntity<UserProfile> createOrUpdateProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileDto userProfileDto
    ) {
        UserProfile updatedProfile = userProfileService.createOrUpdateProfile(userId, userProfileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    // Obtener perfil por ID de usuario
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getProfileByUserId(@PathVariable Long userId) {
        UserProfile userProfile = userProfileService.getProfileByUserId(userId);
        return ResponseEntity.ok(userProfile);
    }
}
