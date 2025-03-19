package com.example.restaurant.controller;

import com.example.restaurant.response.UserInfoResponse;
import com.example.restaurant.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("restaurant/account/setting")
@RequiredArgsConstructor
public class SettingController {

  private final SettingService service;

  @GetMapping
  public ResponseEntity<UserInfoResponse> getInfo(
      @RequestHeader(name = "Authorization", required = false) String authHeader
  ){
    String token = authHeader.substring(7);

    UserInfoResponse user = new UserInfoResponse(
        service.getUser(token)
    );
    log.info("Получение данных о пользователе: {}", user.getLogin());
    return ResponseEntity.ok(user);
  }
}
