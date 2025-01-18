package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.config.JwtTokenProvider;
import ec.edu.uce.marketplace.dtos.AuthResponseDto;
import ec.edu.uce.marketplace.dtos.LoginDto;
import ec.edu.uce.marketplace.dtos.RegisterRequestDto;
import ec.edu.uce.marketplace.dtos.RegisterResponseDto;
import ec.edu.uce.marketplace.entities.Role;
import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.repositories.RoleRepository;
import ec.edu.uce.marketplace.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;
    private final RoleRepository roleRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserServiceImpl userService, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.roleRepository = roleRepository;
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
        User user = userService.findByUsername(loginDto.getUsername()).orElseThrow(
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

    @Override
    public RegisterResponseDto register(RegisterRequestDto registerRequest) {
        // Validar si el correo o el usuario ya existen
        if (userService.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está en uso");
        }

        // Crear el usuario
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setPhoneNumber(registerRequest.getPhoneNumber());

        // Asignar roles
        Set<Role> userRoles = new HashSet<>();
        registerRequest.getRoles().forEach(roleName -> {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("El rol " + roleName + " no existe"));
            userRoles.add(role);
        });

        // Si no se especifican roles, asignar el rol por defecto (ROLE_USER)
        if (userRoles.isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("El rol predeterminado no existe"));
            userRoles.add(defaultRole);
        }
        newUser.setRoles(userRoles);

        // Guardar usuario en la base de datos
        userService.save(newUser);

        return new RegisterResponseDto(newUser.getEmail(), newUser.getUsername(), "ok");
    }

}
