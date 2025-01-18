package ec.edu.uce.marketplace.controllers;

import ec.edu.uce.marketplace.dtos.AuthResponseDto;
import ec.edu.uce.marketplace.dtos.LoginDto;
import ec.edu.uce.marketplace.dtos.RegisterRequestDto;
import ec.edu.uce.marketplace.dtos.RegisterResponseDto;
import ec.edu.uce.marketplace.services.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    // Endpoint para iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        AuthResponseDto authResponseDto = authService.login(loginDto);
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerUser(@Valid @RequestBody RegisterRequestDto registerRequest) {
        RegisterResponseDto registerRequestDto = authService.register(registerRequest);
        return new ResponseEntity<>(registerRequestDto, HttpStatus.OK);
    }
}
