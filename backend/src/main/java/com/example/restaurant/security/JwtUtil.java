package com.example.restaurant.security;

import com.example.restaurant.exception.ExpiredJwtTokenException;
import io.jsonwebtoken.ExpiredJwtException;
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
    try {
      if (isTokenExpiration(token))
        return null;
      return Jwts.parser()
          .setSigningKey(getSignKey(SECRET_KEY))
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
    }
    catch (ExpiredJwtException exception){
      return null;
    }
  }

  public boolean isTokenValid(String token, String login){
    String extractLogin = extractLogin(token);
    return extractLogin.equals(login) && !isTokenExpiration(token);
  }

  public boolean isTokenExpiration(String token){
    try{
      Date expirationDate =Jwts.parser()
          .setSigningKey(getSignKey(SECRET_KEY))
          .parseClaimsJws(token)
          .getBody()
          .getExpiration();
      return expirationDate.before(new Date());
    }
    catch (ExpiredJwtException exception){
      return true;
    }
  }
  private Key getSignKey(String key){
    byte[] keyBytes = Decoders.BASE64.decode(key);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
