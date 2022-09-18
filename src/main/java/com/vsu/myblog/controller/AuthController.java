package com.vsu.myblog.controller;

import com.vsu.myblog.dto.user.TokenRequestDto;
import com.vsu.myblog.dto.user.TokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name="Аутентификация", description = "API для регистрации и входа")
public class AuthController {

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Operation(summary = "Аутентификация для получения access- и refresh-token")
    public ResponseEntity<TokenResponseDto> getToken(@RequestBody TokenRequestDto requestDto) {
        TokenResponseDto response = new TokenResponseDto();
        response.setAccess_token("access_token");
        response.setAccess_token("refresh_token");
        return ResponseEntity.ok(response);
    }

}
