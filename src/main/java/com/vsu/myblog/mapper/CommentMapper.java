package com.vsu.myblog.mapper;

import com.vsu.myblog.dto.comment.CommentCreateDto;
import com.vsu.myblog.dto.comment.CommentDto;
import com.vsu.myblog.dto.comment.CommentUpdateDto;
import com.vsu.myblog.model.entity.CommentEntity;
import org.mapstruct.*;

@Mapper
public interface CommentMapper {

    @Mapping(source = "postId", target = "post.id")
    @Mapping(source = "userId", target = "user.id")
    CommentEntity toEntity(CommentDto dto);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    CommentDto toDto(CommentEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "postId", target = "post.id")
    CommentEntity toEntity(CommentCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommentDto updateDto(@MappingTarget CommentDto target, CommentUpdateDto dto);
}
