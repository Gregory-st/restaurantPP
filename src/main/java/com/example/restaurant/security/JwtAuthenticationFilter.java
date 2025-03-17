package com.example.restaurant.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtService = new JwtUtil();

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

      String authHeader = request.getHeader("Authorization");
      String token = null;
      String login = null;
      String prefix = "Bearer ";

      if(authHeader != null && authHeader.startsWith(prefix)){
        token = authHeader.substring(prefix.length());
        try{
          login = jwtService.extractLogin(token);
        }
        catch (Exception e){
          log.error("Не верный токен");
          filterChain.doFilter(request, response);
          return;
        }
      }

      if(login != null && SecurityContextHolder.getContext().getAuthentication() == null){
        if(jwtService.isTokenValid(token, login)){
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(
                  login,
                  null,
                  new ArrayList<>()
              );
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
      filterChain.doFilter(request, response);
  }
}
