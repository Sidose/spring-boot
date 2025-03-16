package com.example.springboot.mapper;

import com.example.springboot.dto.TaskHistoryResponseDto;
import com.example.springboot.model.TaskHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskHistoryMapper {

  @Mapping(target = "todoId", source = "todo.id")
  TaskHistoryResponseDto toDto(TaskHistory taskHistory);

  TaskHistory toModel(TaskHistoryResponseDto taskHistoryResponseDto);
}
