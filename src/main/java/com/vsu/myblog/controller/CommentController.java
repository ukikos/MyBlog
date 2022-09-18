package com.vsu.myblog.controller;

import com.vsu.myblog.dto.comment.CommentCreateDto;
import com.vsu.myblog.dto.comment.CommentDto;
import com.vsu.myblog.dto.comment.CommentUpdateDto;
import com.vsu.myblog.service.CommentService;
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
@RequestMapping("/api/comments")
@Tag(name="Комментарии", description = "API для работы с комментариями")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    @Secured("ROLE_USER")
    @Operation(summary = "Получить свои комментарии", description = "Доступ: ROLE_USER")
    public ResponseEntity<List<CommentDto>> getCurrentComments() {
        return ResponseEntity.ok(commentService.getCurrentComments());
    }

    @GetMapping("/post/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Получить комментарии под постом по id поста", description = "Доступ: ROLE_USER")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(id));
    }

    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Получить свой комментарий по id", description = "Доступ: ROLE_USER")
    public ResponseEntity<CommentDto> getCurrentCommentByCommentId(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCurrentCommentByCommentId(id));
    }

    @PostMapping("/comment")
    @Secured("ROLE_USER")
    @Operation(summary = "Оставить комментарий под постом по id поста", description = "Доступ: ROLE_USER")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentCreateDto requestDto) {
        return new ResponseEntity<>(commentService.createComment(requestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Редактировать свой комменатрий по id комментария", description = "Доступ: ROLE_USER")
    public ResponseEntity<CommentDto> editCurrentCommentByCommentId(@PathVariable Long id, @RequestBody CommentUpdateDto updateDto) {
        return ResponseEntity.ok(commentService.editCurrentCommentByCommentId(id, updateDto));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Удалить свой комментарий по id комментария", description = "Доступ: ROLE_USER")
    public ResponseEntity<Void> deleteCommentByCommentId(@PathVariable Long id) {
        commentService.deleteCommentByCommentId(id);
        return ResponseEntity.ok().build();
    }
}
