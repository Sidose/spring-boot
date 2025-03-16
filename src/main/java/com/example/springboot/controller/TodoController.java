package com.example.springboot.controller;

import com.example.springboot.dto.TaskHistoryResponseDto;
import com.example.springboot.dto.TodoCreateDto;
import com.example.springboot.dto.TodoResponseDto;
import com.example.springboot.dto.TodoUpdateDto;
import com.example.springboot.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/todos")
public class TodoController {
  private final TodoService todoService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<TodoResponseDto> save(@RequestBody @Valid TodoCreateDto todoDto) {

    TodoResponseDto todoResponseDto = todoService.save(todoDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(todoResponseDto);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}")
  public TodoResponseDto update(@PathVariable Long id, @RequestBody @Valid TodoUpdateDto todoDto) {

    return todoService.update(id, todoDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    todoService.delete(id);
  }

  @GetMapping
  public List<TodoResponseDto> findAll(Authentication authentication, Pageable pageable) {
    String username = authentication.getName();

    return todoService.findAll(username, pageable);
  }

  @GetMapping("/{id}/history")
  public List<TaskHistoryResponseDto> get(@PathVariable Long id) {

    return todoService.findTaskHistory(id);
  }
}
