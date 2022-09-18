package com.vsu.myblog.service;

import com.vsu.myblog.dto.user.RoleDto;
import com.vsu.myblog.exception.NotFoundException;
import com.vsu.myblog.mapper.RoleMapper;
import com.vsu.myblog.model.entity.RoleEntity;
import com.vsu.myblog.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleEntity getRoleEntityByName(String name) {
        return roleRepository.findByRole(name).orElseThrow(() -> {
            log.info("Role with name {} not found", name);
            return new NotFoundException("Role with name (" + name + ") not found");
        });
    }

    public RoleDto getRoleDtoByName(String name) {
        return roleMapper.toDto(getRoleEntityByName(name));
    }

    public RoleEntity getUserRole() {
        return getRoleEntityByName("ROLE_USER");
    }

    public RoleEntity getAdminRole() {
        return getRoleEntityByName("ROLE_ADMIN");
    }

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    //для отладки
    public void saveRoleEntity(RoleEntity roleEntity) {
        roleRepository.save(roleEntity);
    }
}
