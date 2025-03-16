package com.example.springboot.repository;

import com.example.springboot.model.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
  List<TaskHistory> findByTodoId(Long todoId);
}
