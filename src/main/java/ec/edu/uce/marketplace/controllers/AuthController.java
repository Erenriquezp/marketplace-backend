//package ec.edu.uce.marketplace.controllers;
//
//import ec.edu.uce.marketplace.entities.User;
//import ec.edu.uce.marketplace.services.UserService;
//import ec.edu.uce.marketplace.utils.JwtUtil;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final UserService userService;
//    private final AuthenticationManager authenticationManager;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//
//    @Autowired
//    public AuthController(UserService userService,
//                          AuthenticationManager authenticationManager,
//                          PasswordEncoder passwordEncoder,
//                          JwtUtil jwtUtil) {
//        this.userService = userService;
//        this.authenticationManager = authenticationManager;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtUtil = jwtUtil;
//    }
//
//    // Registro de un nuevo usuario
//    @PostMapping("/register")
//    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody User user) {
//        // Verificar si el username ya existe
//        if (userService.existsByUsername(user.getUsername())) {
//            return ResponseEntity.badRequest().body(Map.of("error", "El nombre de usuario ya está en uso"));
//        }
//
//        // Encriptar la contraseña y guardar el usuario
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRole("ROLE_USER"); // Asignar rol predeterminado
//        userService.save(user);
//
//        return ResponseEntity.ok(Map.of("message", "Usuario registrado con éxito"));
//    }
//
//    // Inicio de sesión
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginRequest) {
//        String username = loginRequest.get("username");
//        String password = loginRequest.get("password");
//
//        // Autenticar usuario
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password)
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Generar el token JWT
//        String token = jwtUtil.generateToken(authentication);
//
//        return ResponseEntity.ok(Map.of("token", token));
//    }
//
//    // Endpoint protegido para verificar el token (opcional)
//    @GetMapping("/me")
//    public ResponseEntity<Map<String, Object>> getCurrentUser () {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Map<String, Object> userDetails = new HashMap<>();
//        userDetails.put("username", authentication.getName());
//        userDetails.put("roles", authentication.getAuthorities());
//        return ResponseEntity.ok(userDetails);
//    }
//}