package com.vsu.myblog.controller;

import com.vsu.myblog.dto.user.UserCreateOrUpdateDto;
import com.vsu.myblog.dto.user.UserDto;
import com.vsu.myblog.mapper.UserMapper;
import com.vsu.myblog.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class WelcomeController {

    private final UserMapper userMapper;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome to heroku";
    }

    @GetMapping("/userdtotest/{username}/{password}/{confPassword}")
    public ResponseEntity<UserCreateOrUpdateDto> getDto(@PathVariable String username, @PathVariable String password, @PathVariable String confPassword) {
        UserCreateOrUpdateDto crDto = new UserCreateOrUpdateDto();
        crDto.setUsername(username);
        crDto.setPassword(password);
        crDto.setPassword(confPassword);
        UserEntity user = userMapper.toEntity(crDto);
        UserCreateOrUpdateDto dto = userMapper.toUserCreateOrUpdateDto(user);
        return ResponseEntity.ok(dto);
    }

}
