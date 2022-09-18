package com.vsu.myblog.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsu.myblog.dto.user.TokenRequestDto;
import com.vsu.myblog.dto.user.TokenResponseDto;
import com.vsu.myblog.dto.user.UserDto;
import com.vsu.myblog.exception.BadRequestException;
import com.vsu.myblog.mapper.UserMapper;
import com.vsu.myblog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name="Аутентификация", description = "API для регистрации, входа и обновления токена доступа")
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Operation(summary = "Аутентификация для получения access- и refresh-token")
    public ResponseEntity<TokenResponseDto> getToken(@RequestBody TokenRequestDto requestDto) {
        TokenResponseDto response = new TokenResponseDto();
        response.setAccess_token("access_token");
        response.setAccess_token("refresh_token");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/token/refresh")
    @Operation(summary = "Обновление access-токена с помощью refresh-токена в заголовке Authorizations")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("qwruwhnqofygqwoqhb".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserDto userDto = userService.getUserByUsername(username);
                List<String> authorities = new ArrayList<>();
                authorities.add(userDto.getRole().getRole());
                String accessToken = JWT.create()
                        .withSubject(userDto.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", authorities)
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", "Wrong request. Try to authenticate again");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new BadRequestException("Refresh token is missing");
        }
    }

}
