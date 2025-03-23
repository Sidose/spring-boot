package com.example.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.springboot.model.Priority;
import com.example.springboot.model.Status;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.example.springboot.service.TodoService;
import com.example.springboot.security.JwtUtil;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springboot.dto.TodoCreateDto;
import com.example.springboot.dto.TodoResponseDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = TodoController.class, excludeAutoConfiguration = {
  org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
  org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
})
public class TodoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TodoService todoService;

  @MockBean
  private JwtUtil jwtUtil;

  @MockBean
  private UserDetailsService userDetailsService;

  @Test
  public void createTodo_Success() throws Exception {
    TodoCreateDto requestDto = new TodoCreateDto(
      "Test todo", "Description of test todo", LocalDateTime.of(2025, 03, 30, 15, 30, 00), Priority.HIGH
    );

    TodoResponseDto responseDto = new TodoResponseDto(
      1L,
      "Test todo",
      "Description of test todo",
      LocalDateTime.of(2025, 03, 30, 15, 30, 00),
      Priority.HIGH,
      Status.PENDING,
      LocalDateTime.now(),
      LocalDateTime.now(),
      1L
    );

    Mockito.when(todoService.save(any(TodoCreateDto.class))).thenReturn(responseDto);

    mockMvc.perform(MockMvcRequestBuilders.post("/todos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDto)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(responseDto.id()))
      .andExpect(jsonPath("$.title").value(responseDto.title()))
      .andExpect(jsonPath("$.description").value(responseDto.description()));
  }

  @Test
  public void createTodo_InvalidRequestDto_BadRequest() throws Exception {
    TodoCreateDto invalidRequest = new TodoCreateDto(
      "",
      "Description error",
      LocalDateTime.now().minusDays(1),
      Priority.HIGH
    );

    mockMvc.perform(MockMvcRequestBuilders.post("/todos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(invalidRequest)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors").exists());
  }

  @Test
  public void getAllTodos() throws Exception {
    String email = "test@example.com";
    TodoResponseDto todoFirst = new TodoResponseDto(
      1L,
      "Test todo first",
      "Description for todo first",
      LocalDateTime.now(),
      Priority.HIGH,
      Status.PENDING,
      LocalDateTime.now(),
      LocalDateTime.now(),
      5L
    );

    TodoResponseDto todoSecond = new TodoResponseDto(
      2L,
      "Test todo second",
      "Description for todo second",
      LocalDateTime.now(),
      Priority.LOW,
      Status.IN_PROGRESS,
      LocalDateTime.now(),
      LocalDateTime.now(),
      5L
    );

    Authentication mockAuthentication = Mockito.mock(Authentication.class);
    Mockito.when(mockAuthentication.getName()).thenReturn(email);
    Mockito.when(todoService.findAll(eq(email), any(Pageable.class))).thenReturn(List.of(todoFirst, todoSecond));

    mockMvc.perform(MockMvcRequestBuilders.get("/todos")
        .principal(mockAuthentication)
        .param("page", "0")
        .param("size", "10"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("[0].id").value(todoFirst.id()))
      .andExpect(jsonPath("$[0].title").value(todoFirst.title()))
      .andExpect(jsonPath("$[1].id").value(todoSecond.id()))
      .andExpect(jsonPath("$[1].title").value(todoSecond.title()));
  }
}
