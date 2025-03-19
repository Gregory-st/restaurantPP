package com.example.restaurant.config;

import com.example.restaurant.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Value("${front.address}")
  private String address;
  @Value("${front.port}")
  private Long port;

  private final JwtAuthenticationFilter filter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authManager -> authManager
                .requestMatchers(HttpMethod.GET, "/restaurant/eats").permitAll()
                .requestMatchers("/restaurant/auth/**").permitAll()
                .requestMatchers("/restaurant/setting/**").authenticated()
                .requestMatchers("restaurant/basket/**").authenticated()
                .anyRequest().authenticated()
        )
        .exceptionHandling(exception ->
                exception.authenticationEntryPoint(((request, response, authException) -> {
                  log.warn("Токен не корректен");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"message\": \"Требуется авторизация\"}");
        }))
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
        .cors(Customizer.withDefaults())
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource(){
    StringBuilder origin;
    origin = new StringBuilder();
    origin.append("http://")
        .append(address)
        .append(":")
        .append(port);

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of(origin.toString()));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
