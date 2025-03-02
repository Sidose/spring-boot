package com.example.springboot.service.impl;

import com.example.springboot.dto.TaskHistoryResponseDto;
import com.example.springboot.exception.NoSuchItemException;
import com.example.springboot.model.Todo;
import com.example.springboot.repository.TaskHistoryRepository;
import com.example.springboot.repository.TodoRepository;
import com.example.springboot.service.TaskHistoryService;
import lombok.RequiredArgsConstructor;
import com.example.springboot.mapper.TaskHistoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskHistoryServiceImpl implements TaskHistoryService {

  final private TaskHistoryMapper taskHistoryMapper;
  final private TaskHistoryRepository taskHistoryRepository;
  final private TodoRepository todoRepository;

  @Override
  public List<TaskHistoryResponseDto> getAllByTaskId(final Long taskId) {
    Todo todo = todoRepository.findById(taskId).orElseThrow(() -> new NoSuchItemException("Task is not found."));

    return taskHistoryRepository.findAllByTodo(todo)
        .stream()
        .map(taskHistoryMapper::toDto)
        .toList();
  }
}
