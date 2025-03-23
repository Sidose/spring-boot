package com.example.springboot.service.impl;

import com.example.springboot.dto.user.UserRegistrationRequestDto;
import com.example.springboot.dto.user.UserResponseDto;
import com.example.springboot.exception.RegistrationException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.model.Role;
import com.example.springboot.model.RoleName;
import com.example.springboot.model.User;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;

  public UserResponseDto register(UserRegistrationRequestDto request) {
    if (userRepository.findByEmail(request.email()).isPresent()) {
      throw new RegistrationException("User already exists");
    }

    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
      .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

    User user = new User();

    user.setPassword(passwordEncoder.encode(request.password()));
    user.setEmail(request.email());
    user.setRoles(Set.of(userRole));

    User userSaved = userRepository.save(user);

    return userMapper.toUserResponse(userSaved);
  }
}
