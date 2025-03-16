package com.example.springboot.controller;

import com.example.springboot.dto.TaskHistoryResponseDto;
import com.example.springboot.dto.TodoCreateDto;
import com.example.springboot.dto.TodoResponseDto;
import com.example.springboot.dto.TodoUpdateDto;
import com.example.springboot.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

  @PostMapping
  public TodoResponseDto save(@RequestBody @Valid TodoCreateDto todoDto) {
    return todoService.save(todoDto);
  }

  @PutMapping("/{id}")
  public TodoResponseDto update(@PathVariable Long id, @RequestBody @Valid TodoUpdateDto todoDto) {
    return todoService.update(id, todoDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    todoService.delete(id);
  }

  @GetMapping("/{id}/history")
  public List<TaskHistoryResponseDto> get(@PathVariable Long id) {
    return todoService.findTaskHistory(id);
  }
}
