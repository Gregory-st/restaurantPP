package com.example.restaurant.controller;

import com.example.restaurant.dto.AddBasketDto;
import com.example.restaurant.exception.UndefinedEatByIdException;
import com.example.restaurant.response.BaseResponse;
import com.example.restaurant.response.DeleteResponse;
import com.example.restaurant.response.ShopResponse;
import com.example.restaurant.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant/shop")
@RequiredArgsConstructor
public class ShopController {

  private static final Logger log = LogManager.getLogger(ShopController.class);
  private final ShopService service;

  @GetMapping
  public ResponseEntity<ShopResponse> getProducts(){
    return ResponseEntity.ok(
        new ShopResponse(service.getEats())
    );
  }

  @PostMapping
  public ResponseEntity<BaseResponse> AddBasket(
      @RequestHeader(name = "Authorization", required = false) String headAuth,
      @RequestBody AddBasketDto basketDto
  ){
    if(headAuth.length() <= 7){
      log.warn("Попытка добавления товара в корзину гостевого аккаунта");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new BaseResponse("Требуется авторизация", false, 0));
    }

    String token = headAuth.substring(7);
    try {
      service.addProductInBasket(basketDto, token);
    }
    catch (UndefinedEatByIdException exception){
      BaseResponse response = new DeleteResponse(basketDto.id());
      response.setSuccess(false);
      response.setCode(-1);
      log.error("Обнаружен не корректный идентификатор блюда: {}", basketDto.id());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(response);
    }

    return ResponseEntity.ok(new BaseResponse("", true, 1));
  }
}
