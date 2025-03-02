package com.example.springboot.service;

import com.example.springboot.dto.TaskHistoryResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskHistoryService {
  List<TaskHistoryResponseDto> getAllByTaskId(Long taskId);
}
