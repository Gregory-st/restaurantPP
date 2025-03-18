package com.example.restaurant.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {
  @Value("${jwt.secret}")
  private String SECRET_KEY;

  @Value("${jwt.expiration}")
  private Long expiration;

  public String generateToken(String login){
    return Jwts.builder()
        .setSubject(login)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS256, getSignKey(SECRET_KEY))
        .compact();
  }

  public String extractLogin(String token){
    return Jwts.parser()
        .setSigningKey(getSignKey(SECRET_KEY))
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
        .setSigningKey(getSignKey(SECRET_KEY))
        .parseClaimsJws(token)
        .getBody()
        .getExpiration()
        .before(new Date());
  }
  private Key getSignKey(String key){
    byte[] keyBytes = Decoders.BASE64.decode(key);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
