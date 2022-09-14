package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.PostEntity;
import com.vsu.myblog.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findAllByUser(UserEntity user);

    List<PostEntity> findAllByUserId(Long userId);
}
