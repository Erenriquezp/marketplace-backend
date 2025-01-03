package ec.edu.uce.marketplace.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "your_secret_key_your_secret_key_123"; // 32 bytes mínimo
    private static final long TOKEN_VALIDITY = 3600000; // 1 hora en milisegundos

    private final Key signingKey;
    SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

    public JwtUtil() {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    /**
     * Genera un token JWT para un usuario dado.
     *
     * @param username Nombre de usuario
     * @return Token JWT generado
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(signingKey)
                .compact();
    }

    /**
     * Extrae todos los claims del token JWT.
     *
     * @param token Token JWT
     * @return Claims extraídos del token
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Valida un token JWT verificando su firma y expiración.
     *
     * @param token Token JWT
     * @param username Nombre de usuario esperado
     * @return true si el token es válido
     */
    public boolean validateToken(String token, String username) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject().equals(username) && !isTokenExpired(claims);
    }

    /**
     * Verifica si un token JWT ha expirado.
     *
     * @param claims Claims extraídos del token
     * @return true si el token ha expirado
     */
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
