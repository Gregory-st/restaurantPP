package com.example.restaurant.controller;

import com.example.restaurant.dto.AuthDto;
import com.example.restaurant.dto.RegistrationDto;
import com.example.restaurant.response.AuthResponse;
import com.example.restaurant.response.RegistrationResponse;
import com.example.restaurant.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationService authService;

  @PostMapping
  public ResponseEntity<?> authentication(@RequestBody AuthDto authDto){
    String token = authService.authentication(authDto);

    AuthResponse response = new AuthResponse(token);

    if(token == null){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Не верный логин или пароль");
    }

    return ResponseEntity.ok(response);
  }

  @PostMapping("/reg")
  public ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto){
    String token = authService.registration(registrationDto);
    return ResponseEntity.ok(new RegistrationResponse(token));
  }
}

