package prototype.carpooling.dm3.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import prototype.carpooling.dm3.model.CustomUserDetail;

import java.util.Date;

@Component
@Slf4j
public class AJWTTokenProvider {

    @Value("${app.jwt.secret}")
    private String secret ;
    @Value("${app.jwt.expirationInMs}")
    private int expirationInMS;

    //Token generation
    public String generateToken(Authentication authentication){

        CustomUserDetail userPrincipal = (CustomUserDetail) authentication.getPrincipal();

        Date expirationDate = new Date(new Date().getTime() + expirationInMS);

        return Jwts.builder()
                   .setSubject(userPrincipal.getUsername())
                   .setIssuedAt(new Date())
                   .setExpiration(expirationDate)
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();

    }

    // Retrieve the subject from JWT
    public String getUsernameFromJWT(String token){
        return Jwts.parser()
                            .setSigningKey(secret)
                            .parseClaimsJws(token)
                            .getBody().getSubject();
    }

    // validate token
    public  boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            log.error("Invalid JWT signature");
        }catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
