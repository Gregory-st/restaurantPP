package com.example.restaurant.controller;

import com.example.restaurant.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restaurant/shop")
@RequiredArgsConstructor
public class ShopController {

  private final ShopService service;

  @GetMapping
  public ResponseEntity<?> getProducts(){
    return null;
  }
}
