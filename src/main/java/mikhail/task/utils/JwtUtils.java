package mikhail.task.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtils {
    private static final String ISSUER = "mikhail.task";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.lifetime}")
    private Duration expiresTime;

    public String getToken(UserDetails user) {
        Date issuedAt = new Date();
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(issuedAt)
                .withExpiresAt(new Date(issuedAt.getTime() + expiresTime.toMillis()))
                .withIssuer(ISSUER)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String getEmailFromToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer(ISSUER)
                .build();
        DecodedJWT decoded = jwtVerifier.verify(token);
        return decoded.getSubject();
    }

}
