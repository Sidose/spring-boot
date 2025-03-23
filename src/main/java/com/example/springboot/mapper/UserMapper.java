package com.example.springboot.mapper;

import com.example.springboot.dto.user.UserResponseDto;
import com.example.springboot.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserResponseDto toUserResponse(User user);
}
