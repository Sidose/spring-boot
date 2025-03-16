package com.example.springboot.dto;

import java.time.LocalDateTime;

public record TaskHistoryResponseDto(
  Long id,
  Long todoId,
  String oldState,
  String newState,
  LocalDateTime changeDate,
  String changedBy
) {
}
