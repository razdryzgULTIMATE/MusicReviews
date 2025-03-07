package ru.serega.musicreviews.security.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import ru.serega.musicreviews.entities.Token;
import ru.serega.musicreviews.repos.TokenRepo;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomLogOutHandler implements LogoutHandler {
    private final TokenRepo tokenRepo;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);

        Optional<Token> tokenOpt = tokenRepo.findByAccessToken(token);

        if (tokenOpt.isPresent()) {
            Token tokenEntity = tokenOpt.get();
            tokenEntity.setLoggedOut(true);
            tokenRepo.save(tokenEntity);
        }
    }
}
