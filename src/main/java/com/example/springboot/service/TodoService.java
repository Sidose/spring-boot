package com.example.springboot.service;

import com.example.springboot.dto.TodoCreateDto;
import com.example.springboot.dto.TodoResponseDto;
import com.example.springboot.dto.TodoUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface TodoService {
  TodoResponseDto save(TodoCreateDto createDto);

  TodoResponseDto update(TodoUpdateDto updateDto);

  void delete(Long id);
}
