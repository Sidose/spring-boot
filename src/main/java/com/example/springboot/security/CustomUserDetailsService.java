package com.example.springboot.security;

import com.example.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return (UserDetails) userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
  }
}
