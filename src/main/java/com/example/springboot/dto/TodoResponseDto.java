package com.example.springboot.dto;

import com.example.springboot.model.Priority;
import com.example.springboot.model.Status;

import java.time.LocalDateTime;

public record TodoResponseDto(
  Long id,
  String title,
  String description,
  LocalDateTime dueDate,
  Priority priority,
  Status status,
  LocalDateTime createdDate,
  LocalDateTime updatedDate,
  Long userId
) {
}
