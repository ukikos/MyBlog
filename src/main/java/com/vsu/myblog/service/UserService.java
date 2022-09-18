package com.vsu.myblog.service;

import com.vsu.myblog.dto.user.UserCreateByAdminDto;
import com.vsu.myblog.dto.user.UserCreateOrUpdateDto;
import com.vsu.myblog.dto.user.UserDto;
import com.vsu.myblog.exception.BadRequestException;
import com.vsu.myblog.exception.ForbiddenException;
import com.vsu.myblog.exception.NotFoundException;
import com.vsu.myblog.mapper.UserMapper;
import com.vsu.myblog.model.entity.UserEntity;
import com.vsu.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final StatusService statusService;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("User with username {} not found in the database", username);
            throw new UsernameNotFoundException("User with username" + username + "not found in the database");
        });
        log.info("User found in the database: {}", username);
        boolean nonLocked = userEntity.getStatus().getStatus().equals(statusService.getActiveStatus().getStatus());
        if (!nonLocked) {
            throw new ForbiddenException("Пользователь с id: "+userEntity.getId()+" заблокирован");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getRole().getRole()));
        return new User(userEntity.getUsername(), userEntity.getPassword(), true, true, true, nonLocked, authorities);
    }

    public UserDto registerUser(UserCreateOrUpdateDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            log.error("User with username {} already exists", userDto.getUsername());
            throw new BadRequestException("User with username (" + userDto.getUsername() + ") already exists");
        }
        if (!StringUtils.equals(userDto.getPassword(), userDto.getPasswordConfirm())) {
            log.error("Passwords are not the same");
            throw new BadRequestException("Passwords are not the same");
        }
        UserEntity userEntity = userMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(roleService.getUserRole());
        userEntity.setStatus(statusService.getActiveStatus());
        log.info("Saving new user with username {} to the database", userDto.getUsername());
        UserEntity result = userRepository.save(userEntity);
        return userMapper.toDto(result);
    }

    public UserDto registerAdmin(UserCreateOrUpdateDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            log.error("User with username {} already exists", userDto.getUsername());
            throw new BadRequestException("User with username (" + userDto.getUsername() + ") already exists");
        }
        if (!StringUtils.equals(userDto.getPassword(), userDto.getPasswordConfirm())) {
            log.error("Passwords are not the same");
            throw new BadRequestException("Passwords are not the same");
        }
        UserEntity adminEntity = userMapper.toEntity(userDto);
        adminEntity.setPassword(passwordEncoder.encode(adminEntity.getPassword()));
        adminEntity.setRole(roleService.getAdminRole());
        adminEntity.setStatus(statusService.getActiveStatus());
        log.info("Saving new admin with username {} to the database", userDto.getUsername());
        UserEntity result = userRepository.save(adminEntity);
        return userMapper.toDto(result);
    }

    public UserDto getUserById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new NotFoundException("User with id " + id + " not found");
                }));
    }

    public UserDto getUserByUsername(String username) {
        return userMapper.toDto(userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User with username {} not found", username);
                    return new NotFoundException("User with username (" + username + ") not found");
                }));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto createUser(UserCreateByAdminDto userDto) {
        UserEntity newUser = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(newUser);
    }

    public UserDto blockUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь с id: "+id+" не найден")
        );
        user.setStatus(statusService.getBannedStatus());
        return userMapper.toDto(userRepository.save(user));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
