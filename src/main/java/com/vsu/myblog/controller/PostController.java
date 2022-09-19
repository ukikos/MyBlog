package com.vsu.myblog.controller;

import com.vsu.myblog.dto.post.PostCreateOrUpdateDto;
import com.vsu.myblog.dto.post.PostDto;
import com.vsu.myblog.service.PostService;
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
@RequestMapping("/api/posts")
@Tag(name="Посты", description = "API для работы с постами")
public class PostController {

    private final PostService postService;

    @GetMapping
    @Secured("ROLE_USER")
    @Operation(summary = "Получить свои посты", description = "Доступ: ROLE_USER")
    public ResponseEntity<List<PostDto>> getCurrentPosts() {
        return ResponseEntity.ok(postService.getCurrentPosts());
    }

    @GetMapping("/all")
    @Secured("ROLE_USER")
    @Operation(summary = "Получить все неприватные посты", description = "Доступ: ROLE_USER")
    public ResponseEntity<List<PostDto>> getAllNonPrivatePosts() {
        return ResponseEntity.ok(postService.getAllNonPrivatePosts());
    }

    @GetMapping("/user/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Получить посты по id пользователя", description = "Доступ: ROLE_USER")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostsByUserId(id));
    }

    @GetMapping("/post/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Получить пост по id поста", description = "Доступ: ROLE_USER")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostByPostId(id));
    }

    @PostMapping
    @Secured("ROLE_USER")
    @Operation(summary = "Создать пост. Приватность определяется значением 'None' или 'Private'", description = "Доступ: ROLE_USER")
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateOrUpdateDto requestDto) {
        return new ResponseEntity<>(postService.createPost(requestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Редактировать свой пост по id поста. Приватность определяется значением 'None' или 'Private'", description = "Доступ: ROLE_USER")
    public ResponseEntity<PostDto> editPostById(@PathVariable Long id, @RequestBody PostCreateOrUpdateDto updateDto) {
        return ResponseEntity.ok(postService.editPostById(id, updateDto));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Удалить свой пост по id", description = "Доступ: ROLE_USER")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }
}
