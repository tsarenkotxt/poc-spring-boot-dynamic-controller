package com.example.dynamic.controller.demo;

import com.example.dynamic.controller.demo.UserDynamicControllerGenerator;
import com.example.dynamic.controller.demo.UserDynamicControllerRegister;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class UserDynamicControllerRegisterTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  @Test
  public void testregisterUserController() throws Exception {
    // Arrange
    UserDynamicControllerGenerator userDynamicControllerGenerator = new UserDynamicControllerGenerator(
        new AnnotationConfigReactiveWebApplicationContext());

    // Act and Assert
    thrown.expect(IllegalStateException.class);
    (new UserDynamicControllerRegister(userDynamicControllerGenerator, new RequestMappingHandlerMapping()))
        .registerUserController();
  }
}
