package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.CommentEntity;
import com.vsu.myblog.model.entity.PostEntity;
import com.vsu.myblog.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByUserId(Long userId);

    List<CommentEntity> findAllByPostId(Long postId);

    List<CommentEntity> findAllByUser(UserEntity user);

    List<CommentEntity> findAllByPost(PostEntity post);
}
