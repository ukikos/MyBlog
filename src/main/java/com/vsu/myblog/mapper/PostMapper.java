package com.vsu.myblog.mapper;

import com.vsu.myblog.dto.comment.CommentCreateDto;
import com.vsu.myblog.dto.comment.CommentDto;
import com.vsu.myblog.dto.post.PostCreateDto;
import com.vsu.myblog.dto.post.PostDto;
import com.vsu.myblog.model.entity.CommentEntity;
import com.vsu.myblog.model.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {LikeMapper.class, CommentMapper.class})
public interface PostMapper {

    @Mapping(source = "userId", target = "user.id")
    PostEntity toEntity(PostDto dto);

    @Mapping(source = "user.id", target = "userId")
    PostDto toDto(PostEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    PostEntity toEntity(PostCreateDto dto);
}
