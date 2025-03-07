package ru.serega.musicreviews.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.serega.musicreviews.dtos.AuthenticationResponseDTO;
import ru.serega.musicreviews.dtos.LoginRequestDTO;
import ru.serega.musicreviews.dtos.RegistrationDTO;
import ru.serega.musicreviews.services.AuthService;
import ru.serega.musicreviews.services.UserService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authenticationService;
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody RegistrationDTO registration) {
        try{
            authenticationService.register(registration);
        }catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                return ResponseEntity.badRequest().body("Имя пользователя уже занято");
            }
        }
        return ResponseEntity.ok("Регистрация прошла успешно");
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/refresh_token")
    public ResponseEntity<AuthenticationResponseDTO> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        return authenticationService.refreshToken(request, response);
    }

}
