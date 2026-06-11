package io.student.rococo.controller;

import io.student.rococo.model.UserJson;
import io.student.rococo.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/users")
public class InternalUserController {

  private final UserService userService;

  @Autowired
  public InternalUserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  public List<UserJson> getAllUsers() {
    return userService.getAllUsers();
  }
}
