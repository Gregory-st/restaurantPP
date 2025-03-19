package com.example.restaurant.response;

import com.example.restaurant.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {
  private String name;
  private String email;
  private String login;

  public UserInfoResponse(UserEntity user){
    name = user.getName();
    email = user.getEmail();
    login = user.getLogin();
  }
}
