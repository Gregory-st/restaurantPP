package com.example.restaurant.service;

import com.example.restaurant.dto.AuthDto;
import com.example.restaurant.dto.RegistrationDto;
import com.example.restaurant.entity.UserEntity;
import com.example.restaurant.repository.UserRepository;
import com.example.restaurant.security.JwtUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private final JwtUtil jwt;
  private final UserRepository userRepository;

  public String registration(RegistrationDto registrationDto){
    boolean isExist = userRepository
        .findUserEntitiesByLogin(registrationDto.login())
        .isPresent();

    if (isExist) return null;

    UserEntity user = UserEntity.builder()
        .name(registrationDto.name())
        .email(registrationDto.email())
        .login(registrationDto.login())
        .password(passwordEncoder.encode(
            registrationDto.password()
        ))
        .build();

    userRepository.save(user);

    return jwt.generateToken(user.getLogin());
  }

  public String authentication(AuthDto authDto){
    Optional<UserEntity> optionalUserEntity;
    if(authDto.login().contains("@")){
      optionalUserEntity = userRepository
          .findUserEntitiesByEmail(authDto.login());
    }
    else {
      optionalUserEntity = userRepository
          .findUserEntitiesByLogin(authDto.login());
    }

    boolean isExist = optionalUserEntity
        .isPresent();

    if(!isExist) return null;

    UserEntity entity = optionalUserEntity.get();
    boolean isValid = passwordEncoder.matches(authDto.password(), entity.getPassword());

    if(!isValid) return null;

    return jwt.generateToken(authDto.login());
  }
}
