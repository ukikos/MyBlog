package com.vsu.myblog.mapper;

import com.vsu.myblog.dto.user.StatusDto;
import com.vsu.myblog.model.entity.StatusEntity;
import org.mapstruct.Mapper;

@Mapper
public interface StatusMapper {

    StatusEntity toEntity(StatusDto dto);

    StatusDto toDto(StatusEntity entity);
}
