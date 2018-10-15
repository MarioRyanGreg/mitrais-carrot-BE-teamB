package com.mitrais.carrot.config.jwt;

import io.jsonwebtoken.*;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * generate jwt token
 *
 * @author Febri_MW251
 */
@Component
public class JwtTokenProvider {

    /**
     * jwt expiration time
     */
    @Value("${app.jwtSecret}")
    private String JWT_SECRET;

    /**
     * jwt expiration time
     */
    @Value("${app.jwtExpirationInMs}")
    private int JWT_EXPIRATION_TIME;

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	/**
	 * generate bearer token
	 *
	 * @param authentication Authentication variable dependency injection
	 * @return String of JWT token that generated
	 */
	public String generateToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		return this.generateMyTokenPlease(Long.toString(userPrincipal.getId()));
	}

	/**
	 * 
	 * @param userPrincipal
	 * @return
	 */
	public String generateToken(UserPrincipal userPrincipal) {
		return this.generateMyTokenPlease(Long.toString(userPrincipal.getId()));
	}

    /**
     * generate token
     * 
     * @param subject string of object to identify Jwts
     * @return String of token value
     */
    private String generateMyTokenPlease(String subject) {
    	Date expiryDate = new Date(System.currentTimeMillis() + this.JWT_EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, this.JWT_SECRET)
                .compact();
    }

    /**
     * get user by jwt token
     *
     * @param token String of JWT token
     * @return Long PK of user that get from JWT subject 
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    /**
     * validate token value
     *
     * @param authToken String token JWT
     * @return Boolean true if token is valid else false
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
