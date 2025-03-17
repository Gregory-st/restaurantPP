package com.example.restaurant.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {
  @Value("${jwt.secret}")
  private String SECRET_KEY;
  @Value("${jwt.expiration}")
  private Long EXPIRATION;

  public String generateToken(String login){
    return Jwts.builder()
        .setSubject(login)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  public String extractLogin(String token){
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean isTokenValid(String token, String login){
    String extractLogin = extractLogin(token);
    return extractLogin.equals(login) && !isTokenExpiration(token);
  }

  private boolean isTokenExpiration(String token){
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody()
        .getExpiration()
        .before(new Date());
  }
}
