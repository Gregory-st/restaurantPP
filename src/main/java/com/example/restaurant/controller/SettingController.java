package com.example.restaurant.controller;

import com.example.restaurant.dto.UpdatePasswordDto;
import com.example.restaurant.dto.UpdateUserDto;
import com.example.restaurant.response.BaseResponse;
import com.example.restaurant.response.UpdateUserResponse;
import com.example.restaurant.response.UserInfoResponse;
import com.example.restaurant.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("restaurant/account/setting")
@RequiredArgsConstructor
public class SettingController {

  private final SettingService service;
  private final int prefixAuthLen = 7;

  private boolean isValidLength(String header){
    boolean isValid = header.length() > prefixAuthLen;
    if(!isValid) {
      log.error("Токен не найден");
    }
    return isValid;
  }

  @GetMapping
  public ResponseEntity<BaseResponse> getInfo(
      @RequestHeader(name = "Authorization", required = false) String authHeader
  ){
    if(!isValidLength(authHeader)){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(
              new BaseResponse("Требуется авторизация", false)
          );
    }

    String token = authHeader.substring(prefixAuthLen);

    UserInfoResponse user = new UserInfoResponse(
        service.getUser(token)
    );
    log.info("Получение данных о пользователе: {}", user.getLogin());
    return ResponseEntity.ok(user);
  }

  @PutMapping("/info")
  public ResponseEntity<BaseResponse> updateInfo(
      @RequestHeader(name = "Authorization", required = false) String authHeader,
      @RequestBody UpdateUserDto updateDto
  ){
    String token = authHeader.substring(prefixAuthLen);
    BaseResponse response;
    token = service.updateUser(token, updateDto);

    log.info("Обновление информации о пользователи логин: {}", updateDto.login());

    response = new UpdateUserResponse(token);
    response.setMessage("Успешное обновление!");
    return ResponseEntity.ok(response);
  }

  @PutMapping("/pass")
  public ResponseEntity<BaseResponse> updatePassword(
      @RequestHeader(name = "Authorization", required = false) String authHeader,
      @RequestBody UpdatePasswordDto passwordDto
  ){
    BaseResponse response = new BaseResponse();

    String token = authHeader.substring(prefixAuthLen);
    boolean isUpdate = service.updatePassword(token, passwordDto);

    if(isUpdate) response.setMessage("Пароль обновлён");
    else {
      response.setMessage("Пароли не совпадают");
      response.setSuccess(false);
      log.warn("Пароли не совпадают");
      return ResponseEntity.badRequest().body(response);
    }

    response.setSuccess(true);
    log.info("Пароль пользователя обновлён");
    return ResponseEntity.ok(response);
  }

  @DeleteMapping
  public ResponseEntity<BaseResponse> deleteUser(
      @RequestHeader(name = "Authorization", required = false) String authHeader
  ){
    String token = authHeader.substring(prefixAuthLen);
    service.deleteUser(token);

    log.info("Пользователь удалён");

    return ResponseEntity.ok(
        new BaseResponse("Пользователь удалён", true)
    );
  }
}
