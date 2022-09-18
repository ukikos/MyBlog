package com.vsu.myblog.controller;

import com.vsu.myblog.dto.comment.CommentDto;
import com.vsu.myblog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/comments")
@Tag(name="Комментарии. Администратор", description = "API для работы администратора с комментариями")
public class AdminCommentController {

    private final CommentService commentService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Получить все комментарии", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("/user/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Получить комментарии по id пользователя", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<List<CommentDto>> getCommentsByUserIdAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentsByUserIdAdmin(id));
    }

    @GetMapping("/post/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Получить комментарии по id поста", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<List<CommentDto>> getCommentsByPostIdAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentsByPostIdAdmin(id));
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Получить комментарий по id комментария", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<CommentDto> getCommentByCommentIdAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentByCommentIdAdmin(id));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Удалить комментарий по id комментария", description = "Доступ: ROLE_ADMIN")
    public ResponseEntity<Void> deleteCommentByCommentIdAdmin(@PathVariable Long id) {
        commentService.deleteCommentByCommentIdAdmin(id);
        return ResponseEntity.ok().build();
    }
}
