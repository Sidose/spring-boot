package com.example.springboot.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDto(
  @NotBlank String email,
  @NotBlank @Size(min = 10, max = 30) String password,
  @NotBlank @Size(min = 10, max = 30) String repeatPassword
) {
}
