package com.vsu.myblog.service;

import com.vsu.myblog.dto.comment.CommentCreateDto;
import com.vsu.myblog.dto.comment.CommentDto;
import com.vsu.myblog.dto.comment.CommentUpdateDto;
import com.vsu.myblog.exception.BadRequestException;
import com.vsu.myblog.exception.ForbiddenException;
import com.vsu.myblog.exception.NotFoundException;
import com.vsu.myblog.mapper.CommentMapper;
import com.vsu.myblog.model.entity.CommentEntity;
import com.vsu.myblog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final PostService postService;
    private final CommentMapper commentMapper;

    public List<CommentDto> getCurrentComments() {
        return userService.getCurrentUserEntity().getComments().stream().map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        if (subscriptionService.isSubscribedToUserOrIsItCurrentUser(postService.getUserEntityOwnerByPostId(postId).getId())) {
            return commentRepository.findAllByPostId(postId).stream().map(commentMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            log.info("Post not found or private");
            throw new NotFoundException("Post not found or private");
        }
    }

    public CommentDto getCurrentCommentByCommentId(Long commentId) {
        CommentEntity comment = commentRepository.findByUserIdAndId(userService.getCurrentId(), commentId)
                .orElseThrow(() -> {
                    log.info("Comment with id: {} not found or you not owner", commentId);
                    throw new NotFoundException("Comment with id: "+commentId+"not found or you not owner");
                });
        return commentMapper.toDto(comment);
    }

    public CommentDto createComment(CommentCreateDto requestDto) {
        if (requestDto.getContent().isBlank()) {
            throw new BadRequestException("Comment content is empty");
        }
        if (!Objects.equals(postService.getPostByPostId(requestDto.getPostId()).getPrivacy(), "None")) {
            //пост неприватный можно комментить
            CommentEntity newComment = commentMapper.toEntity(requestDto);
            newComment.setUser(userService.getCurrentUserEntity());
            CommentEntity result = commentRepository.save(newComment);
            return commentMapper.toDto(result);
        } else {
            if (Objects.equals(postService.getPostByPostId(requestDto.getPostId()).getUserId(), userService.getCurrentId())) {
                //наш пост приватный, можно комментить вдоволь
                CommentEntity newComment = commentMapper.toEntity(requestDto);
                newComment.setUser(userService.getCurrentUserEntity());
                CommentEntity result = commentRepository.save(newComment);
                return commentMapper.toDto(result);
            }
            if (subscriptionService.isSubscribedToUser(postService.getPostByPostId(requestDto.getPostId()).getUserId())) {
                //приватный пост, но мы подписчик
                CommentEntity newComment = commentMapper.toEntity(requestDto);
                newComment.setUser(userService.getCurrentUserEntity());
                CommentEntity result = commentRepository.save(newComment);
                return commentMapper.toDto(result);
            }
            //приватный пост, мы не подписчик
            log.error("You are not allowed to comment this post");
            throw new ForbiddenException("You are not allowed to comment this post");
        }
    }

    public CommentDto editCurrentCommentByCommentId(Long commentId, CommentUpdateDto updateDto) {
        if (updateDto.getContent().isBlank()) {
            throw new BadRequestException("Comment content is empty");
        } else {
            Long currentId = userService.getCurrentId();
            CommentEntity commentEntity = commentRepository.findByUserIdAndId(currentId, commentId)
                    .orElseThrow(() -> {
                        log.error("Comment with id: {} of user with id: {} not found", commentId, currentId);
                        throw new NotFoundException("Comment with id: "+commentId+" of user with " +
                                "id: "+currentId+" not found");
                    });
            CommentDto commentDto = commentMapper.toDto(commentEntity);
            CommentDto updatedComment = commentMapper.updateDto(commentDto, updateDto);
            commentRepository.save(commentMapper.toEntity(updatedComment));
            return updatedComment;
        }
    }

    public void deleteCommentByCommentId(Long commentId) {
        if (commentRepository.existsByUserIdAndId(userService.getCurrentId(), commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            log.error("Comment with id: {} not found or you not author", commentId);
            throw new NotFoundException("Comment with id: "+commentId+" not found or you not author");
        }
    }

    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream().map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByUserIdAdmin(Long userId) {
        return commentRepository.findAllByUserId(userId).stream().map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByPostIdAdmin(Long postId) {
        return commentRepository.findAllByPostId(postId).stream().map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public CommentDto getCommentByCommentIdAdmin(Long commentId) {
        return commentMapper.toDto(commentRepository.findById(commentId).orElseThrow(() -> {
            log.info("Comment with id: {} not found", commentId);
            throw new NotFoundException("Comment with id: "+commentId+" not found");
        }));
    }

    public void deleteCommentByCommentIdAdmin(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            log.info("Comment with id: {} not found", commentId);
            throw new NotFoundException("Comment with id: "+commentId+" not found");
        }
    }
}
