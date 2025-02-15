package desafio.backend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // Use Keys.secretKeyFor to generate a secure key with the correct size
    private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);  // 256-bit key
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNING_KEY)  // Use SIGNING_KEY, which is generated securely
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);  // If invalid, throws exception
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public static boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private static Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)  // Use the generated SIGNING_KEY
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
