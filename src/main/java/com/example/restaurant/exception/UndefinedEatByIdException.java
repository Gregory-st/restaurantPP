package com.example.restaurant.exception;

public class UndefinedEatByIdException extends RuntimeException {

  public UndefinedEatByIdException(String message) {
    super(message);
  }
  public UndefinedEatByIdException(){
    super("Блюдо не найдено");
  }

}
