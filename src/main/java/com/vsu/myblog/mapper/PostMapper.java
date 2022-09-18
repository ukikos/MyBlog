package com.vsu.myblog.mapper;

import com.vsu.myblog.dto.post.PostCreateOrUpdateDto;
import com.vsu.myblog.dto.post.PostDto;
import com.vsu.myblog.model.entity.PostEntity;
import org.mapstruct.*;

@Mapper(uses = {LikeMapper.class, CommentMapper.class})
public interface PostMapper {

    @Mapping(source = "userId", target = "user.id")
    PostEntity toEntity(PostDto dto);

    @Mapping(source = "user.id", target = "userId")
    PostDto toDto(PostEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "user", ignore = true)
    PostEntity toEntity(PostCreateOrUpdateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PostDto updateDto(@MappingTarget PostDto target, PostCreateOrUpdateDto dto);

}
