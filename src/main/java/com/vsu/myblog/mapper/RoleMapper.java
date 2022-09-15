package com.vsu.myblog.mapper;

import com.vsu.myblog.dto.user.RoleDto;
import com.vsu.myblog.model.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {

    RoleDto toDto(RoleEntity entity);

    RoleEntity toEntity(RoleDto dto);
}
