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
import com.example.springboot.model.User;
import com.example.springboot.repository.TaskHistoryRepository;
import com.example.springboot.repository.TodoRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.TodoService;
import lombok.RequiredArgsConstructor;
import com.example.springboot.mapper.TodoMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class TodoServiceImpl implements TodoService {

  private final TaskHistoryMapper taskHistoryMapper;
  private final TaskHistoryRepository taskHistoryRepository;
  private final TodoMapper todoMapper;
  private final TodoRepository todoRepository;
  private final UserRepository userRepository;

  private Long getCurrentUserId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      User user = (User) principal;
      return user.getId();
    } else {
      throw new IllegalStateException("User is not authenticated");
    }
  }

  private String getCurrentUserName() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      User user = (User) principal;

      return user.getUsername();
    } else {
      throw new IllegalStateException("User is not authenticated");
    }
  }

  @Override
  public TodoResponseDto save(final TodoCreateDto createDto) {
    Long userId = getCurrentUserId();
    log.info("user {} with email {} is trying to create todo", userId, getCurrentUserName());

    Todo todo = todoMapper.toModel(createDto);
    todo.setStatus(Status.PENDING);
    todo.setUserId(userId);
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
    todo.setUserId(getCurrentUserId());

    TaskHistory taskHistory = new TaskHistory();
    taskHistory.setTodo(existTodo);
    taskHistory.setOldState(existTodo.toString());

    Todo updatedTodo = todoRepository.save(todo);

    taskHistory.setNewState(updatedTodo.toString());
    taskHistory.setChangeDate(LocalDateTime.now());
    taskHistory.setChangedBy(getCurrentUserName());

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
  public List<TodoResponseDto> findAll(String email, Pageable pageable) {
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found."));

    return todoRepository.findAllByUserId(user.getId(), pageable)
      .stream()
      .map(todoMapper::toDto)
      .toList();
  }

  @Override
  public List<TaskHistoryResponseDto> findTaskHistory(Long id) {
    todoRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Todo with id " + id + " not found."));

    List<TaskHistory> historyList = taskHistoryRepository.findByTodoId(id);

    return historyList.stream()
      .map(taskHistoryMapper::toDto)
      .collect(Collectors.toList());
  }
}
