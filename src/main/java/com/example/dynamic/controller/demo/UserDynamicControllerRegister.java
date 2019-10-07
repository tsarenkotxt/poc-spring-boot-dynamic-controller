package com.example.dynamic.controller.demo;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;

/**
 * Registers rest controller for {@link User}
 *
 * @see UserDynamicControllerGenerator
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserDynamicControllerRegister {

    private final UserDynamicControllerGenerator controllerGenerator;
    private final RequestMappingHandlerMapping handlerMapping;

    @PostConstruct
    @SneakyThrows
    public void registerUserController() {
        Object userController = controllerGenerator.generateUserController();

        // do some `if/else` business logic here...

        /**
         * Delegates to:
         * {@link UserControllerMethodsImplementation#getAll()}
         */
        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/users/")
                        .methods(RequestMethod.GET)
                        .produces(MediaType.APPLICATION_JSON_VALUE)
                        .build(),
                userController,
                userController.getClass()
                        .getMethod("getAll"));

        /**
         * Delegates to:
         * {@link UserControllerMethodsImplementation#get(Long)}
         */
        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/users")
                        .methods(RequestMethod.GET)
                        .produces(MediaType.APPLICATION_JSON_VALUE)
                        .build(),
                userController,
                userController.getClass()
                        .getMethod("getById", Long.class));

        /**
         * Delegates to:
         * {@link UserControllerMethodsImplementation#save(User)}
         */
        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/users")
                        .methods(RequestMethod.POST)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .build(),
                userController,
                userController.getClass()
                        .getMethod("save", User.class));

        /**
         * Delegates to:
         * {@link UserControllerMethodsImplementation#update(Long, User)}
         */
        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/users")
                        .methods(RequestMethod.PUT)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .build(),
                userController,
                userController.getClass()
                        .getMethod("update", Long.class, User.class));

        /**
         * Delegates to:
         * {@link UserControllerMethodsImplementation#updateNickName(Long, String)}
         */
        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/users")
                        .methods(RequestMethod.PATCH)
                        .build(),
                userController,
                userController.getClass()
                        .getMethod("updateNickName", Long.class, String.class));

        /**
         * Delegates to:
         * {@link UserControllerMethodsImplementation#delete(Long)}
         */
        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/users/{id}")
                        .methods(RequestMethod.DELETE)
                        .build(),
                userController,
                userController.getClass()
                        .getMethod("delete", Long.class));

        log.info("Registered request handler for `DynamicController`: {}", userController.getClass().getName());
    }

}
