package com.example.springboot.service;

import com.example.springboot.dto.TaskHistoryResponseDto;
import com.example.springboot.dto.TodoCreateDto;
import com.example.springboot.dto.TodoResponseDto;
import com.example.springboot.dto.TodoUpdateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoService {
  TodoResponseDto save(TodoCreateDto createDto);

  TodoResponseDto update(Long id, TodoUpdateDto updateDto);

  void delete(Long id);

  List<TodoResponseDto> findAll(String email, Pageable pageable);

  List<TaskHistoryResponseDto> findTaskHistory(Long id);
}
