package ec.edu.uce.marketplace.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AuthResponseDto {
    private String accessToken;
    private List<String> roles; // Cambiado para manejar múltiples roles
    private Long id;

    public AuthResponseDto(String accessToken, List<String> roles, Long id) {
        this.accessToken = accessToken;
        this.roles = roles;
        this.id = id;
    }
}
