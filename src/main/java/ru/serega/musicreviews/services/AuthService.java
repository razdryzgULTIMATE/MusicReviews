package ru.serega.musicreviews.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import ru.serega.musicreviews.dtos.AuthenticationResponseDTO;
import ru.serega.musicreviews.dtos.LoginRequestDTO;
import ru.serega.musicreviews.dtos.RegistrationDTO;

public interface AuthService {
    void register(RegistrationDTO registration);
    AuthenticationResponseDTO authenticate(LoginRequestDTO request);
    ResponseEntity<AuthenticationResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response);
}
