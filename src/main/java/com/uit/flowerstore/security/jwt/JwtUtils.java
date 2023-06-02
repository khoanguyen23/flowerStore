package com.uit.flowerstore.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.uit.flowerstore.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;


@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${bezkoder.app.jwtSecret}")
  private String jwtSecret;

  @Value("${bezkoder.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(UserDetailsImpl userPrincipal) {
    return generateTokenFromUsername(userPrincipal.getUsername());
  }

  public String generateTokenFromUsername(String username) {
    Key jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    Key jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    Claims claims = Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Key jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
      Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(authToken);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (io.jsonwebtoken.ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (io.jsonwebtoken.MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (io.jsonwebtoken.UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }
}
