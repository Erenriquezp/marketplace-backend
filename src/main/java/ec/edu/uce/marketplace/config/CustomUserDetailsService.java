package ec.edu.uce.marketplace.config;

import ec.edu.uce.marketplace.entities.User;
import ec.edu.uce.marketplace.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Buscar al usuario por nombre de usuario
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not exists by Username"));

        // Convertir los roles del usuario a GrantedAuthority
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Mapear cada rol a SimpleGrantedAuthority
                .collect(Collectors.toSet());

        // Crear y retornar el objeto UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), // Nombre de usuario
                user.getPassword(), // Contraseña codificada
                authorities         // Autoridades (roles del usuario)
        );
    }

}
