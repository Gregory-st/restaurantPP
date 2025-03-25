package com.example.restaurant.dto;

public record AddEatDto(
    String name,
    String description,
    int price,
    int weight,
    int proteins,
    int fats,
    int carbon,
    String image
) {

}
