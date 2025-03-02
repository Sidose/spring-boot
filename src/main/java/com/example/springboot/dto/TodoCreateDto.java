package com.example.springboot.dto;

import com.example.springboot.model.Priority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record TodoCreateDto(
  @NotEmpty(message = "Title can not be empty") @Length(max = 100) String title,
  @Length(max = 500) String description,
  @NotNull LocalDateTime dueDate,
  Priority priority
) {
}
