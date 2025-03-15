package com.example.springboot.service.impl;

import com.example.springboot.dto.user.UserRegistrationRequestDto;
import com.example.springboot.dto.user.UserResponseDto;
import com.example.springboot.exception.RegistrationException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException {
    if (userRepository.findByEmail(request.email()).isPresent()) {
      throw new RegistrationException("User already exists");
    }

    User user = new User();

    user.setPassword(passwordEncoder.encode(request.password()));
    user.setEmail(request.email());

    User userSaved = userRepository.save(user);

    return userMapper.toUserResponse(userSaved);
  }
}
