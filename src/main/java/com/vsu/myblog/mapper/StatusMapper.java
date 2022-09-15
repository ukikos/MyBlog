package com.vsu.myblog.mapper;

import com.vsu.myblog.dto.user.StatusDto;
import com.vsu.myblog.dto.user.UserDto;
import com.vsu.myblog.model.entity.StatusEntity;
import com.vsu.myblog.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface StatusMapper {

    StatusEntity toEntity(StatusDto dto);

    StatusDto toDto(StatusEntity entity);
}
