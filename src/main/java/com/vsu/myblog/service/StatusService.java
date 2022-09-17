package com.vsu.myblog.service;

import com.vsu.myblog.dto.user.StatusDto;
import com.vsu.myblog.exception.NotFoundException;
import com.vsu.myblog.mapper.StatusMapper;
import com.vsu.myblog.model.entity.StatusEntity;
import com.vsu.myblog.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    public StatusEntity getStatusEntityByName(String name) {
        return statusRepository.findByStatus(name).orElseThrow(() -> {
            log.info("Status with name {} not found", name);
            return new NotFoundException("Status with name (" + name + ") not found");
        });
    }

    public StatusDto getStatusDtoByName(String name) {
        return statusMapper.toDto(getStatusEntityByName(name));
    }

    public StatusEntity getActiveStatus() {
        return getStatusEntityByName("ACTIVE");
    }

    public StatusEntity getBannedStatus() {
        return getStatusEntityByName("BANNED");
    }

    public List<StatusDto> getAllStatuses() {
        return statusRepository.findAll().stream()
                .map(statusMapper::toDto)
                .collect(Collectors.toList());
    }

    //для отладки
    public void saveStatusEntity(StatusEntity statusEntity) {
        statusRepository.save(statusEntity);
    }
}
