package com.example.dynamic.controller.demo;

import com.example.dynamic.controller.demo.UserDynamicControllerGenerator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;

public class UserDynamicControllerGeneratorTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  @Test
  public void testgenerateUserController() throws Exception {
    // Arrange, Act and Assert
    thrown.expect(IllegalStateException.class);
    (new UserDynamicControllerGenerator(new AnnotationConfigReactiveWebApplicationContext())).generateUserController();
  }
}
