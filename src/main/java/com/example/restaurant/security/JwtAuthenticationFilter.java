package com.example.restaurant.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

      String authHeader = request.getHeader("Authorization");
      String token = null;
      String login = null;
      String prefix = "Bearer ";

      if(authHeader == null || !authHeader.startsWith(prefix) || authHeader.length() < 8){
          log.warn("Не верный токен: {}", token);
          filterChain.doFilter(request, response);
          return;
      }

      token = authHeader.substring(prefix.length());

      if(token.equals("null")) {
        log.warn("Не верный токен: {}", token);
        filterChain.doFilter(request, response);
        return;
      }

      login = jwtService.extractLogin(token);

      if(login != null && SecurityContextHolder.getContext().getAuthentication() == null){
        if(jwtService.isTokenValid(token, login)){
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(
                  login,
                  null,
                  new ArrayList<>()
              );

          authToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request)
          );

          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
      filterChain.doFilter(request, response);
  }
}
