package com.vsu.myblog.controller;

import com.vsu.myblog.dto.post.LikeDto;
import com.vsu.myblog.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@Tag(name="Лайки", description = "API для работы с лайками")
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    @Secured("ROLE_USER")
    @Operation(summary = "Получить все свои лайки постов", description = "Доступ: ROLE_USER")
    public ResponseEntity<List<LikeDto>> getAllCurrentLikes() {
        return ResponseEntity.ok(likeService.getAllCurrentLikes());
    }

    @PostMapping("/like/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Поставить лайк посту по id поста", description = "Доступ: ROLE_USER")
    public ResponseEntity<LikeDto> likePostByPostId(@PathVariable Long id) {
        return new ResponseEntity<>(likeService.likePostByPostId(id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Убрать лайк с поста по id поста", description = "Доступ: ROLE_USER")
    public ResponseEntity<Void> deleteLikeByPostId(@PathVariable Long id) {
        likeService.deleteLikeByPostId(id);
        return ResponseEntity.ok().build();
    }
}
