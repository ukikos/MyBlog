package com.vsu.myblog;

import com.vsu.myblog.dto.user.UserCreateOrUpdateDto;
import com.vsu.myblog.model.entity.RoleEntity;
import com.vsu.myblog.model.entity.StatusEntity;
import com.vsu.myblog.service.RoleService;
import com.vsu.myblog.service.StatusService;
import com.vsu.myblog.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBlogApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService, StatusService statusService) {
        return args -> {
            statusService.saveStatusEntity(new StatusEntity(null, "ACTIVE"));
            statusService.saveStatusEntity(new StatusEntity(null, "BANNED"));

            roleService.saveRoleEntity(new RoleEntity(null, "ROLE_USER", null));
            roleService.saveRoleEntity(new RoleEntity(null, "ROLE_ADMIN", null));

            userService.registerUser(new UserCreateOrUpdateDto("sus", "12345", "12345"));
            userService.registerUser(new UserCreateOrUpdateDto("Dmitrii", "55555", "55555"));
            userService.registerUser(new UserCreateOrUpdateDto("Chinchopa", "qwerty", "qwerty"));
            userService.registerUser(new UserCreateOrUpdateDto("Nagibator", "333333", "333333"));
        };
    }

}
