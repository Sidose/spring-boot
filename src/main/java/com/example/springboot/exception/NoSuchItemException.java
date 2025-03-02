package com.example.springboot.exception;

public class NoSuchItemException extends RuntimeException {
  public NoSuchItemException(String message) {
    super(message);
  }
}
