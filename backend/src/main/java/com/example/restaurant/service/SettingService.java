package com.example.restaurant.service;

import com.example.restaurant.dto.UpdatePasswordDto;
import com.example.restaurant.dto.UpdateUserDto;
import com.example.restaurant.entity.UserEntity;
import com.example.restaurant.exception.ExpiredJwtTokenException;
import com.example.restaurant.exception.UndefinedUserByIdException;
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
    if(jwt.isTokenExpiration(token)) throw new ExpiredJwtTokenException("Токен истёк");

    String login = jwt.extractLogin(token);
    return userRepository
        .findUserEntitiesByLogin(login)
        .orElseGet(() ->
            userRepository
                .findUserEntitiesByEmail(login)
                .orElseThrow(UndefinedUserByIdException::new)
        );
  }

  public String updateUser(String token, UpdateUserDto updateDto){
    String login = jwt.extractLogin(token);
    UserEntity user = userRepository
        .findUserEntitiesByLogin(login)
        .orElseGet(() ->
            userRepository
                .findUserEntitiesByEmail(login)
                .orElseThrow(UndefinedUserByIdException::new)
        );

    user.setEmail(updateDto.email());
    user.setName(updateDto.name());
    user.setLogin(updateDto.login());

    userRepository.save(user);

    return jwt.generateToken(user.getLogin());
  }

  public boolean updatePassword(String token, UpdatePasswordDto passwordDto){
    String login = jwt.extractLogin(token);
    UserEntity user = userRepository
        .findUserEntitiesByLogin(login)
        .orElseGet(() ->
            userRepository
                .findUserEntitiesByEmail(login)
                .orElseThrow(UndefinedUserByIdException::new)
        );

    boolean isEquals = passwordEncoder.matches(
        passwordDto.oldPassword(),
        user.getPassword()
    );

    if(isEquals){
      String passwordEncode = passwordEncoder
          .encode(passwordDto.password());

      user.setPassword(passwordEncode);
      userRepository.save(user);
    }

    return isEquals;
  }

  public void deleteUser(String token){
    String login = jwt.extractLogin(token);
    UserEntity user = userRepository
        .findUserEntitiesByLogin(login)
        .orElseGet(() ->
            userRepository
                .findUserEntitiesByEmail(login)
                .orElseThrow(UndefinedUserByIdException::new)
        );

    userRepository.delete(user);
  }
}
