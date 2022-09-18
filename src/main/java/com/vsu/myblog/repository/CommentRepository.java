package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.CommentEntity;
import com.vsu.myblog.model.entity.PostEntity;
import com.vsu.myblog.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByUserId(Long userId);

    List<CommentEntity> findAllByPostId(Long postId);

    List<CommentEntity> findAllByUser(UserEntity user);

    List<CommentEntity> findAllByPost(PostEntity post);

    Optional<CommentEntity> findByUserIdAndId(Long userId, Long id);

    boolean existsByUserIdAndId(Long userId, Long id);
}
