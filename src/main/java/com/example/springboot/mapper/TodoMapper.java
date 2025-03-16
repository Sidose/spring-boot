package com.example.springboot.mapper;

import com.example.springboot.dto.TodoCreateDto;
import com.example.springboot.dto.TodoResponseDto;
import com.example.springboot.dto.TodoUpdateDto;
import com.example.springboot.model.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

  TodoResponseDto toDto(Todo todo);

  Todo toModel(TodoCreateDto requestDto);

  Todo toModel(TodoUpdateDto updateDto);
}
