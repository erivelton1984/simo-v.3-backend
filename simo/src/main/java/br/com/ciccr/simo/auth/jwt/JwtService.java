package br.com.ciccr.simo.auth.jwt;

import br.com.ciccr.simo.modules.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getKey() {
        byte[] key = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(key);
    }

    public String generateToken(User user) {

        return Jwts.builder()

                .subject(user.getEmail())

                .claim("id", user.getId())

                .claim("name", user.getFullName())

                .claim("role", user.getRole().getName().name())

                .issuedAt(new Date())

                .expiration(new Date(System.currentTimeMillis() + expiration))

                .signWith(getKey())

                .compact();
    }

    public String extractUsername(String token) {

        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {

        return !extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}