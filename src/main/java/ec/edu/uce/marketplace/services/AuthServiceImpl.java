package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.config.JwtTokenProvider;
import ec.edu.uce.marketplace.dtos.AuthResponseDto;
import ec.edu.uce.marketplace.dtos.LoginDto;
import ec.edu.uce.marketplace.entities.Role;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        // 01 - Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 02 - Generar el token
        String token = jwtTokenProvider.generateToken(authentication);

        // 03 - Obtener el usuario desde la base de datos
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );

        // 04 - Extraer los roles como una lista de nombres de roles
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();
        //
        Long userId = user.getId();
        // 05 - Retornar el token y los roles en el DTO de respuesta
        return new AuthResponseDto(token, roles, userId);
    }

}
