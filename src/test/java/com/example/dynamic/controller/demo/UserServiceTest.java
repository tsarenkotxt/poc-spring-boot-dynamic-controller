package com.example.dynamic.controller.demo;

import static org.junit.Assert.assertEquals;
import com.example.dynamic.controller.demo.User;
import com.example.dynamic.controller.demo.UserService;
import java.util.List;
import org.junit.Test;

public class UserServiceTest {
  @Test
  public void testsetUp() throws Exception {
    // Arrange
    UserService userService = new UserService();

    // Act
    userService.setUp();

    // Assert
    User getResult = (userService.getAll()).get(0);
    String lastName = getResult.getLastName();
    String firstName = getResult.getFirstName();
    Long id = getResult.getId();
    assertEquals("stubLastName", lastName);
    assertEquals("stubNickName", getResult.getNickName());
    assertEquals(Long.valueOf(1L), id);
    assertEquals("stubFirstName", firstName);
  }
}
