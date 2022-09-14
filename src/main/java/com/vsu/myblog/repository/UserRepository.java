package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    boolean existsByUsernameOrEmail(String username, String email);
}
