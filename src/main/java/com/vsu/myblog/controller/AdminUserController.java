package com.vsu.myblog.controller;

import com.vsu.myblog.dto.user.UserCreateByAdminDto;
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
@RequestMapping("/api/admin/users")
@Tag(name="Администратор", description = "API для работы администратора с пользователями")
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Получить список всех пользователей", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Получить пользователя по id", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Создать пользователя", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateByAdminDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Заблокировать пользователя по id", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<UserDto> blockUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.blockUserById(id));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Удалить пользователя по id", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}
