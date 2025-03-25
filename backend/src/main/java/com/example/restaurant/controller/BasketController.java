package com.example.restaurant.controller;

import com.example.restaurant.exception.ExpiredJwtTokenException;
import com.example.restaurant.response.BaseResponse;
import com.example.restaurant.response.ShopResponse;
import com.example.restaurant.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restaurant/basket")
@RequiredArgsConstructor
public class BasketController {

  private static final Logger log = LogManager.getLogger(BasketController.class);
  private final BasketService service;

  @GetMapping
  public ResponseEntity<BaseResponse> getAll(
      @RequestHeader(name = "Authorization", required = false) String headAuth
  ){
    if(headAuth.length() <= 7){
      log.warn("Токен не найден {}", headAuth);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(
              new BaseResponse("Требуется авторизация", false, 0)
          );
    }
    String token = headAuth.substring(7);
    BaseResponse response;

    try{
      response = new ShopResponse(service.getBasket(token));
      response.setCode(1);
      response.setSuccess(true);
    } catch (Exception exception) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(
              new BaseResponse("Требуется авторизация", false, 0)
          );
    }

    return ResponseEntity.ok(response);
  }
}
