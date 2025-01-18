package ec.edu.uce.marketplace.services;

import ec.edu.uce.marketplace.dtos.AuthResponseDto;
import ec.edu.uce.marketplace.dtos.LoginDto;
import ec.edu.uce.marketplace.dtos.RegisterRequestDto;
import ec.edu.uce.marketplace.dtos.RegisterResponseDto;

public interface AuthService {
    AuthResponseDto login(LoginDto loginDto);
    RegisterResponseDto register(RegisterRequestDto registerRequestDto);
}
