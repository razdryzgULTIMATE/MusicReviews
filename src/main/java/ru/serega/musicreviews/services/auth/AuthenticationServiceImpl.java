package ru.serega.musicreviews.services.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.serega.musicreviews.dtos.AuthenticationResponseDTO;
import ru.serega.musicreviews.dtos.LoginRequestDTO;
import ru.serega.musicreviews.dtos.RegistrationDTO;
import ru.serega.musicreviews.entities.Token;
import ru.serega.musicreviews.entities.User;
import ru.serega.musicreviews.repos.TokenRepo;
import ru.serega.musicreviews.repos.UserRepo;
import ru.serega.musicreviews.services.jwt.JwtService;
import ru.serega.musicreviews.util.Role;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthService {
    private final UserRepo userRepo;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenRepo tokenRepo;

    //+ регистрация пользователя
    @Override
    public void register(RegistrationDTO registration) {
        User user = new User();
        user.setUsername(registration.getUsername());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRegistrationDate(LocalDate.now());
        user.setRole(Role.USER);
        userRepo.save(user);
    }

    @Override
    public AuthenticationResponseDTO authenticate(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllToken(user);
        saveUserToken(accessToken, refreshToken, user);
        return new AuthenticationResponseDTO(accessToken, refreshToken);
    }

    @Override
    public ResponseEntity<AuthenticationResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userRepo
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));
        if (!jwtService.isValidRefresh(token, user)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllToken(user);
        saveUserToken(accessToken, refreshToken, user);
        return ResponseEntity.ok(new AuthenticationResponseDTO(accessToken, refreshToken));
    }

    //+ аннулирует все действующие токены
    private void revokeAllToken(User user) {
        List<Token> tokens = tokenRepo.findAllAcessTokenByUser(user.getId());
        if (!tokens.isEmpty()) {
            tokens.forEach(t -> t.setLoggedOut(true));
        }
        tokenRepo.saveAll(tokens);
    }

    //+ Сохранить токены
    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false); // токен активен
        token.setUser(user);
        tokenRepo.save(token);
    }

}
