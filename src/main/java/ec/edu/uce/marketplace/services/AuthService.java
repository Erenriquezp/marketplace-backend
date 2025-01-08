package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.dtos.AuthResponseDto;
import ec.edu.uce.marketplace.dtos.LoginDto;

public interface AuthService {
    public AuthResponseDto login(LoginDto loginDto);
}
