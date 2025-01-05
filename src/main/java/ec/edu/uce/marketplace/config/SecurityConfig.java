package ec.edu.uce.marketplace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF para APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Permitir acceso público a rutas de autenticación
                        .requestMatchers("/api/users/**").permitAll() // Permitir acceso público a rutas de autenticación
                        .requestMatchers("/api/products/**").permitAll() // Permitir acceso público a rutas de autenticación
                        .anyRequest().authenticated() // Resto de rutas requiere autenticación
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::disable); // Configuración de JWT como OAuth2 Resource Server

        return http.build();
    }
}
