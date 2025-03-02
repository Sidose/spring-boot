package com.example.springboot.repository;

import com.example.springboot.dto.TaskHistoryResponseDto;
import com.example.springboot.model.TaskHistory;
import com.example.springboot.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistoryResponseDto, Long> {
  List<TaskHistory> findAllByTodo(Todo todo);

}
