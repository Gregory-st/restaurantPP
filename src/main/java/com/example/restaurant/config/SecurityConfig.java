package com.example.restaurant.config;

import com.example.restaurant.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
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

  @Value("${front.hosts}")
  private String hosts;

  private final JwtAuthenticationFilter filter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authManager -> authManager
                .requestMatchers(HttpMethod.GET, "/restaurant/shop").permitAll()
                .requestMatchers("/restaurant/auth/**").permitAll()
                .requestMatchers("/restaurant/setting/**").authenticated()
                .anyRequest().authenticated()
        )
        .exceptionHandling(exception ->
                exception.authenticationEntryPoint(((request, response, authException) -> {
                  log.warn("Токен не корректен");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }))
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
        .cors(Customizer.withDefaults())
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource(){
    List<String> origins = Arrays.stream(hosts.split(","))
        .toList();

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(origins);
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
