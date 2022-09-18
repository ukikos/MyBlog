package com.vsu.myblog.controller;

import com.vsu.myblog.dto.user.UserCreateOrUpdateDto;
import com.vsu.myblog.dto.user.UserDto;
import com.vsu.myblog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name="Пользователи", description = "API для работы с пользователями")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Secured("ROLE_USER")
    @Operation(summary = "Получить данные текущего пользователя", description = "Доступ: ROLE_USER")
    public ResponseEntity<UserDto> getProfile() {
        return ResponseEntity.ok(userService.getProfile());
    }

    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Получить пользователя по id", description = "Доступ: ROLE_USER")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    @Secured("ROLE_USER")
    @Operation(summary = "Получить пользователя по юзернейму", description = "Доступ: ROLE_USER")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping
    @Secured("ROLE_USER")
    @Operation(summary = "Редактировать текущего пользователя", description = "Доступ: ROLE_USER")
    public ResponseEntity<UserDto> editCurrentUser(@RequestBody UserCreateOrUpdateDto userCreateOrUpdateDto) {

        return ResponseEntity.ok(userService.editCurrentUser(userCreateOrUpdateDto));
    }

    @DeleteMapping
    @Secured("ROLE_USER")
    @Operation(summary = "Удалить текущего пользователя", description = "Доступ: ROLE_USER")
    public ResponseEntity<Void> deleteCurrentUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.ok().build();
    }


}
