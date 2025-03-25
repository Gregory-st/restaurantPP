package com.example.restaurant.exception;

public class UndefinedUserByIdException extends RuntimeException {

  public UndefinedUserByIdException(String message) {
    super(message);
  }
  public UndefinedUserByIdException(){
    super("Пользователь не найден");
  }
}
