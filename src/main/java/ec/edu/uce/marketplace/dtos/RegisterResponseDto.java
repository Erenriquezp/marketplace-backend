package ec.edu.uce.marketplace.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponseDto {
    private String email;
    private String username;
    private String message;
}
