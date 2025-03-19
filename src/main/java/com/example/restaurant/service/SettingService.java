package com.example.restaurant.service;

import com.example.restaurant.entity.UserEntity;
import com.example.restaurant.repository.UserRepository;
import com.example.restaurant.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingService {
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private final JwtUtil jwt;
  private final UserRepository userRepository;

  public UserEntity getUser(String token){
    String login = jwt.extractLogin(token);
    return userRepository.findUserEntitiesByLogin(login).get();
  }
}
