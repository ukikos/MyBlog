package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.LikeEntity;
import com.vsu.myblog.model.entity.PostEntity;
import com.vsu.myblog.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    List<LikeEntity> findAllByUserId(Long userId);

    List<LikeEntity> findAllByPostId(Long postId);

    List<LikeEntity> findAllByUser(UserEntity user);

    List<LikeEntity> findAllByPost(PostEntity post);
}
