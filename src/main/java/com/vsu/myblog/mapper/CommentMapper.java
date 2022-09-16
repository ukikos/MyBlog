package com.vsu.myblog.mapper;

import com.vsu.myblog.dto.comment.CommentCreateDto;
import com.vsu.myblog.dto.comment.CommentDto;
import com.vsu.myblog.model.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {

    @Mapping(source = "postId", target = "post.id")
    @Mapping(source = "userId", target = "user.id")
    CommentEntity toEntity(CommentDto dto);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    CommentDto toDto(CommentEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "postId", target = "post.id")
    @Mapping(source = "userId", target = "user.id")
    CommentEntity toEntity(CommentCreateDto dto);
}
