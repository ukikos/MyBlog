package com.vsu.myblog.mapper;

import com.vsu.myblog.dto.user.UserCreateOrUpdateDto;
import com.vsu.myblog.dto.user.UserDto;
import com.vsu.myblog.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {RoleMapper.class, StatusMapper.class})
public interface UserMapper {

    UserEntity toEntity(UserDto dto);

    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UserCreateOrUpdateDto requestDto);

    UserCreateOrUpdateDto toUserCreateOrUpdateDto(UserEntity entity);
}
