package ru.serega.musicreviews.services.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.serega.musicreviews.entities.User;
import ru.serega.musicreviews.repos.TokenRepo;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${security.token.secret}")
    private String secret;
    @Value("${security.token.acess-token-expiration}")
    private long accessTokenExpiration;
    @Value("${security.token.refresh-token-expiration}")
    private long refreshTokenExpiration;
    private final TokenRepo tokenRepo;

    public JwtServiceImpl(TokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    private String generateToken(User user, long expiryTime) {
        JwtBuilder builder = Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiryTime))
                .signWith(getSecretKey());
        return builder.compact();
    }

    private Claims extractAllClaims(String token) {
        JwtParserBuilder parser = Jwts.parser().verifyWith(getSecretKey());
        return parser.build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpiration);
    }

    @Override
    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpiration);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> f) {
        Claims claims = extractAllClaims(token);
        return f.apply(claims);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        boolean isValidToken = tokenRepo
                .findByAccessToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);
        return username
                .equals(user.getUsername())
                && isAccessTokenExpired(token)
                && isValidToken;
    }

    @Override
    public boolean isValidRefresh(String token, User user) {
        String username = extractUsername(token);

        boolean isValidRefreshToken = tokenRepo.findByRefreshToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);
        return username
                .equals(user.getUsername())
                && isAccessTokenExpired(token)
                && isValidRefreshToken;
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private boolean isAccessTokenExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }


}
