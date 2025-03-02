package com.example.springboot.controller;

import com.example.springboot.dto.TaskHistoryResponseDto;
import com.example.springboot.dto.TodoCreateDto;
import com.example.springboot.dto.TodoResponseDto;
import com.example.springboot.dto.TodoUpdateDto;
import com.example.springboot.service.TaskHistoryService;
import com.example.springboot.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/todos")
public class TodoController {
  private final TaskHistoryService taskHistoryService;
  private final TodoService todoService;

  @PostMapping
  public TodoResponseDto save(@RequestBody @Valid TodoCreateDto todoDto) {
    return todoService.save(todoDto);
  }

  @PutMapping("/{id}")
  public TodoResponseDto update(@RequestBody @Valid TodoUpdateDto todoDto, @PathVariable Long id) {
    return todoService.update(todoDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    todoService.delete(id);
  }

  @GetMapping("/{id}/history")
  public List<TaskHistoryResponseDto> get(@PathVariable Long id) {
    return taskHistoryService.getAllByTaskId(id);
  }
}
