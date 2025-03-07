package ru.serega.musicreviews.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import ru.serega.musicreviews.entities.User;

import java.util.function.Function;

public interface JwtService {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    <T> T extractClaim(String token, Function<Claims, T> f);
    String extractUsername(String token);
    boolean isValid(String token, UserDetails user);
    boolean isValidRefresh(String token, User user);
}
