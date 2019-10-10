package prototype.carpooling.dm3.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import prototype.carpooling.dm3.model.CustomUserDetail;

import java.util.Date;

@Component
@Slf4j
public class AJWTTokenProvider {

    // later set those values on .properties
    private String JWTSecret = "MyJWTSuperSecretKey";
    private int JWTExpirationInMS = 888800000;

    //Token generation
    public String generateToken(Authentication authentication){

        CustomUserDetail userPrincipal = (CustomUserDetail) authentication.getPrincipal();

        Date expirationDate = new Date(new Date().getTime() + JWTExpirationInMS);

        return Jwts.builder()
                   .setSubject(userPrincipal.getUsername())
                   .setIssuedAt(new Date())
                   .setExpiration(expirationDate)
                   .signWith(SignatureAlgorithm.HS512, JWTSecret)
                   .compact();

    }

    // Retrieve the subject from JWT
    public String getUsernameFromJWT(String token){
        return Jwts.parser()
                            .setSigningKey(JWTSecret)
                            .parseClaimsJws(token)
                            .getBody().getSubject();
    }

    // validate token
    public  boolean validateToken(String token){
        log.info("validateToken() token is:["+token+"]");
        log.info("validateToken() jwtSecret is:["+JWTSecret+"]");
        try {
            Jwts.parser().setSigningKey(JWTSecret).parseClaimsJws(token);
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
