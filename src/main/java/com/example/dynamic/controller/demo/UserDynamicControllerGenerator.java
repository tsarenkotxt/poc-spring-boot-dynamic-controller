package com.example.dynamic.controller.demo;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Argument;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Generates rest controller for {@link User} at runtime:
 * {@code
 *
 * package com.example.dynamic.controller.demo;
 *
 * import com.example.dynamic.controller.demo.UserDynamicControllerGenerator.UserControllerMethodsImplementation;
 *
 * import java.util.List;
 *
 * import org.springframework.web.bind.annotation.PathVariable;
 * import org.springframework.web.bind.annotation.RequestParam;
 * import org.springframework.web.bind.annotation.RestController;
 *
 * @RestController
 * public class UserDynamicController {
 *     public List getAll() {
 *         return UserControllerMethodsImplementation.getAll();
 *     }
 *
 *     public User getById(@RequestParam(name = "id") Long id) {
 *         return UserControllerMethodsImplementation.get(id);
 *     }
 *
 *     public void save(@RequestBody User user) {
 *         UserControllerMethodsImplementation.save(user);
 *     }
 *
 *     public void update(@RequestParam(name = "id") Long id, @RequestBody User user) {
 *         UserControllerMethodsImplementation.update(id, user);
 *     }
 *
 *     public void updateNickName(@RequestParam(name = "id") Long id, @RequestParam(name = "nickName") String nickName) {
 *         UserControllerMethodsImplementation.updateNickName(id, nickName);
 *     }
 *
 *     public void delete(@PathVariable(name = "id") Long id) {
 *         UserControllerMethodsImplementation.delete(id);
 *     }
 *
 *     public UserDynamicController() {
 *     }
 * }
 *
 * }
 */
@Slf4j
@Component
@DependsOn("userService")
@RequiredArgsConstructor
public class UserDynamicControllerGenerator {

    private final ApplicationContext applicationContext;

    @SneakyThrows
    public Object generateUserController() {
        // init static implementation to avoid reflection usage
        UserControllerMethodsImplementation.userService = applicationContext.getBean(UserService.class);

        // creates builder with unique `class` name and `@RestController` annotation
        Object userController = new ByteBuddy()
                .subclass(Object.class)
                .name("UserDynamicController")
                .annotateType(AnnotationDescription.Builder
                        .ofType(RestController.class) // don't use `request` mapping here
                        .build())

                // do some `if/else` business logic here...

                /**
                 * Delegates to:
                 * {@link UserControllerMethodsImplementation#getAll()}
                 */
                .defineMethod("getAll", List.class, Modifier.PUBLIC)
                .intercept(MethodDelegation.to(UserControllerMethodsImplementation.class))

                /**
                 * Delegates to:
                 * {@link UserControllerMethodsImplementation#get(Long)}
                 */
                .defineMethod("getById", User.class, Modifier.PUBLIC)
                .withParameter(Long.class, "id")
                .annotateParameter(AnnotationDescription.Builder
                        .ofType(RequestParam.class)
                        .define("name", "id")
                        .build())
                .intercept(MethodDelegation.to(UserControllerMethodsImplementation.class))

                /**
                 * Delegates to:
                 * {@link UserControllerMethodsImplementation#save(User)}
                 */
                .defineMethod("save", void.class, Modifier.PUBLIC)
                .withParameter(User.class, "user")
                .annotateParameter(AnnotationDescription.Builder
                        .ofType(RequestBody.class)
                        .build())
                .intercept(MethodDelegation.to(UserControllerMethodsImplementation.class))

                /**
                 * Delegates to:
                 * {@link UserControllerMethodsImplementation#update(Long, User)}
                 */
                .defineMethod("update", void.class, Modifier.PUBLIC)
                .withParameter(Long.class, "id")
                .annotateParameter(AnnotationDescription.Builder
                        .ofType(RequestParam.class)
                        .define("name", "id")
                        .build())
                .withParameter(User.class, "user")
                .annotateParameter(AnnotationDescription.Builder
                        .ofType(RequestBody.class)
                        .build())
                .intercept(MethodDelegation.to(UserControllerMethodsImplementation.class))

                /**
                 * Delegates to:
                 * {@link UserControllerMethodsImplementation#updateNickName(Long, String)}
                 */
                .defineMethod("updateNickName", void.class, Modifier.PUBLIC)
                .withParameter(Long.class, "id")
                .annotateParameter(AnnotationDescription.Builder
                        .ofType(RequestParam.class)
                        .define("name", "id")
                        .build())
                .withParameter(String.class, "nickName")
                .annotateParameter(AnnotationDescription.Builder
                        .ofType(RequestParam.class)
                        .define("name", "nickName")
                        .build())
                .intercept(MethodDelegation.to(UserControllerMethodsImplementation.class))

                /**
                 * Delegates to:
                 * {@link UserControllerMethodsImplementation#delete(Long)}
                 */
                .defineMethod("delete", void.class, Modifier.PUBLIC)
                .withParameter(Long.class, "id")
                .annotateParameter(AnnotationDescription.Builder
                        .ofType(PathVariable.class)
                        .define("name", "id")
                        .build())
                .intercept(MethodDelegation.to(UserControllerMethodsImplementation.class))

                // creates instance of generated `controller`
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance();

        log.info("Generated `DynamicController`: {}", userController.getClass().getName());
        return userController;
    }

    /**
     * Methods implementation for {@link User} controller by {@link UserService}
     */
    public static class UserControllerMethodsImplementation {

        private static UserService userService;

        /**
         * Delegates to:
         * {@link UserService#save(User)}
         */
        public static List<User> getAll() {
            return userService.getAll();
        }

        /**
         * Delegates to:
         * {@link UserService#getById(Long)}
         */
        public static User get(@Argument(0) Long id) { // don't use primitive types
            return userService.getById(id);
        }

        /**
         * Delegates to:
         * {@link UserService#save(User)}
         */
        public static void save(@Argument(0) User user) {
            userService.save(user);
        }

        /**
         * Delegates to:
         * {@link UserService#update(Long, User)}
         */
        public static void update(@Argument(0) Long id, // don't use primitive types
                                  @Argument(1) User user) {
            userService.update(id, user);
        }

        /**
         * Delegates to:
         * {@link UserService#updateNickName(Long, String)}
         */
        public static void updateNickName(@Argument(0) Long id, // don't use primitive types
                                          @Argument(1) String nickName) {
            userService.updateNickName(id, nickName);
        }

        /**
         * Delegates to:
         * {@link UserService#delete(Long)}
         */
        public static void delete(@Argument(0) Long id) { // don't use primitive types
            userService.delete(id);
        }

    }

}
