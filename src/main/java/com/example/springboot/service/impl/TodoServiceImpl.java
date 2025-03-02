package com.example.springboot.service.impl;

import com.example.springboot.dto.TodoCreateDto;
import com.example.springboot.dto.TodoResponseDto;
import com.example.springboot.dto.TodoUpdateDto;
import com.example.springboot.model.Todo;
import com.example.springboot.repository.TodoRepository;
import com.example.springboot.service.TodoService;
import lombok.RequiredArgsConstructor;
import com.example.springboot.mapper.TodoMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

  final private TodoMapper todoMapper;
  final private TodoRepository todoRepository;

  @Override
  public TodoResponseDto save(final TodoCreateDto createDto) {
    Todo todo = todoMapper.toModel(createDto);

    return todoMapper.toDto(todoRepository.save(todo));
  }

  @Override
  public TodoResponseDto update(TodoUpdateDto updateDto) {
    Todo todo = todoMapper.toModel(updateDto);

    return todoMapper.toDto(todoRepository.save(todo));
  }

  @Override
  public void delete(Long id) {
    todoRepository.deleteById(id);
  }
}
