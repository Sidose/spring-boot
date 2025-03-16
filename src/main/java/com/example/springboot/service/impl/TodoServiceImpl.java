package com.example.springboot.service.impl;

import com.example.springboot.dto.TaskHistoryResponseDto;
import com.example.springboot.dto.TodoCreateDto;
import com.example.springboot.dto.TodoResponseDto;
import com.example.springboot.dto.TodoUpdateDto;
import com.example.springboot.exception.ResourceNotFoundException;
import com.example.springboot.mapper.TaskHistoryMapper;
import com.example.springboot.model.Status;
import com.example.springboot.model.TaskHistory;
import com.example.springboot.model.Todo;
import com.example.springboot.repository.TaskHistoryRepository;
import com.example.springboot.repository.TodoRepository;
import com.example.springboot.service.TodoService;
import lombok.RequiredArgsConstructor;
import com.example.springboot.mapper.TodoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

  private final TaskHistoryMapper taskHistoryMapper;
  private final TaskHistoryRepository taskHistoryRepository;
  private final TodoMapper todoMapper;
  private final TodoRepository todoRepository;

  @Override
  public TodoResponseDto save(final TodoCreateDto createDto) {
    Todo todo = todoMapper.toModel(createDto);
    todo.setStatus(Status.PENDING);
    todo.setUserId(1L);
    todo.setCreatedAt(LocalDateTime.now());
    todo.setUpdatedAt(LocalDateTime.now());

    return todoMapper.toDto(todoRepository.save(todo));
  }

  @Override
  public TodoResponseDto update(Long id, TodoUpdateDto updateDto) {
    Todo existTodo = todoRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Todo with id " + id + " not found."));

    Todo todo = todoMapper.toModel(updateDto);
    todo.setId(id);
    todo.setCreatedAt(existTodo.getCreatedAt());
    todo.setUpdatedAt(existTodo.getUpdatedAt());

    TaskHistory taskHistory = new TaskHistory();
    taskHistory.setTodo(existTodo);
    taskHistory.setOldState(existTodo.toString());

    Todo updatedTodo = todoRepository.save(todo);

    taskHistory.setNewState(updatedTodo.toString());
    taskHistory.setChangeDate(LocalDateTime.now());

    return todoMapper.toDto(updatedTodo);
  }

  @Override
  public void delete(Long id) {
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Todo with " + id + " not found."));

    todo.setDeleted(true);
    todoRepository.save(todo);
  }

  @Override
  public List<TaskHistoryResponseDto> findTaskHistory(Long id) {
    Todo todo = todoRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Todo with id " + id + " not found."));

    List<TaskHistory> historyList = taskHistoryRepository.findByTodoId(id);

    return historyList.stream()
      .map(taskHistoryMapper::toDto)
      .collect(Collectors.toList());
  }
}
