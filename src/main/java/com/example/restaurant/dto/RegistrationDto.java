package com.example.restaurant.dto;

public record RegistrationDto(
    String name,
    String email,
    String login,
    String password,
    String repeatPassword
    ) {
}
