package com.example.restaurant.dto;

public record UpdatePasswordDto(
    String oldPassword,
    String password
) {

}
