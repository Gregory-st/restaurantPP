package com.example.restaurant.controller;

import com.example.restaurant.dto.AuthDto;
import com.example.restaurant.dto.RegistrationDto;
import com.example.restaurant.response.AuthResponse;
import com.example.restaurant.response.RegistrationResponse;
import com.example.restaurant.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

  private final AuthenticationService authService;

  @PostMapping
  public ResponseEntity<?> authentication(@RequestBody AuthDto authDto){
    String token = authService.authentication(authDto);

    AuthResponse response = new AuthResponse(token);
    if(token == null){
      log.warn("Incorrect login or password: {}", authDto.login());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Не верный логин или пароль");
    }

    log.info("Authentication User: {}", authDto.login());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/reg")
  public ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto){
    String token = authService.registration(registrationDto);

    if(token == null) {;
      log.warn("User Exist: {}", registrationDto.login());
      return ResponseEntity.badRequest().body("Пользователь уже существует");
    }

    log.info("Success Registration: {}", registrationDto.email());
    return ResponseEntity.ok(new RegistrationResponse(token));
  }
}

