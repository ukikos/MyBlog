package com.vsu.myblog.mapper;

import com.vsu.myblog.dto.user.UserCreateOrUpdateDto;
import com.vsu.myblog.dto.user.UserDto;
import com.vsu.myblog.model.entity.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = {RoleMapper.class, StatusMapper.class})
public interface UserMapper {

    UserEntity toEntity(UserDto dto);

    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UserCreateOrUpdateDto requestDto);

    UserCreateOrUpdateDto toUserCreateOrUpdateDto(UserEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity updateEntity(@MappingTarget UserEntity target, UserCreateOrUpdateDto dto);

}
