package com.vsu.myblog.mapper;

import com.vsu.myblog.dto.post.LikeCreateDto;
import com.vsu.myblog.dto.post.LikeDto;
import com.vsu.myblog.model.entity.LikeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LikeMapper {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "postId", target = "post.id")
    LikeEntity toEntity(LikeDto dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "post.id", target = "postId")
    LikeDto toDto(LikeEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "postId", target = "post.id")
    LikeEntity toEntity(LikeCreateDto dto);
}
