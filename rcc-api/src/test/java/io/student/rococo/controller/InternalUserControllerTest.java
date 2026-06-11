package io.student.rococo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.student.rococo.model.UserJson;
import io.student.rococo.service.api.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InternalUserControllerTest {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private UserService userService;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    userService = mock(UserService.class);
    mockMvc = MockMvcBuilders
        .standaloneSetup(new InternalUserController(userService))
        .build();
  }

  @Test
  @Order(1)
  void shouldReturnEmptyUsersList() throws Exception {
    when(userService.getAllUsers()).thenReturn(List.of());

    mockMvc.perform(get("/internal/users/all"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(List.of())));
  }

  @Test
  @Order(2)
  void shouldReturnNonEmptyUsersList() throws Exception {
    List<UserJson> users = List.of(
        new UserJson(
            UUID.randomUUID(),
            "test-user",
            "Test",
            "User",
            null
        )
    );
    when(userService.getAllUsers()).thenReturn(users);

    mockMvc.perform(get("/internal/users/all"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(users)));
  }
}
