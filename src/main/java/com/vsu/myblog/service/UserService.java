package com.vsu.myblog.service;

import com.vsu.myblog.dto.user.UserDto;
import com.vsu.myblog.mapper.UserMapper;
import com.vsu.myblog.model.entity.UserEntity;
import com.vsu.myblog.repository.RoleRepository;
import com.vsu.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDto saveUser(UserDto userDto) {
        UserEntity userEntity = userRepository.save(userMapper.toEntity(userDto));
        log.info("Saving new user to the database");
        return userMapper.toDto(userEntity);
    }

    public UserDto getUser(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("User with id {} not found", id);
                    return new NotFoundException("User with id " + id + " not found");
                }));
    }

    public UserDto getUser(String username) {
        return userMapper.toDto(userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.info("User with username {} not found", username);
                    return new NotFoundException("User with username " + username + " not found");
                }));
    }
}
