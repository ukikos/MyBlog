package com.vsu.myblog.controller;

import com.vsu.myblog.dto.post.PostDto;
import com.vsu.myblog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/posts")
@Tag(name="Посты. Администратор", description = "API для работы администратора с постами")
public class AdminPostController {

    private final PostService postService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Получить все посты", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<List<PostDto>> getAllPostsAdmin() {
        return ResponseEntity.ok(postService.getAllPostsAdmin());
    }

    @GetMapping("/user/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Получить посты по id пользователя", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<List<PostDto>> getPostsByUserIdAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostsByUserIdAdmin(id));
    }

    @GetMapping("/post/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Получить пост по id поста", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<PostDto> getPostByPostIdAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostByPostIdAdmin(id));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Удалить пост по id", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<Void> deletePostByIdAdmin(@PathVariable Long id) {
        postService.deletePostByIdAdmin(id);
        return ResponseEntity.ok().build();
    }
}
