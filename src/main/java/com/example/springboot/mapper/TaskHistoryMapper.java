package com.example.springboot.mapper;

import com.example.springboot.config.MapperConfig;
import com.example.springboot.dto.TaskHistoryResponseDto;
import com.example.springboot.model.TaskHistory;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TaskHistoryMapper {
  TaskHistoryResponseDto toDto(TaskHistory taskHistory);

  TaskHistory toModel(TaskHistoryResponseDto taskHistoryResponseDto);
}
