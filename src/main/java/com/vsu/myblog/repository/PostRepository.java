package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.PostEntity;
import com.vsu.myblog.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findAllByUser(UserEntity user);

    List<PostEntity> findAllByUserId(Long userId);

    @Query(value = "select p from PostEntity p where p.privacy = 'None'")
    List<PostEntity> findAllNonPrivate();

    @Query(value = "select p from PostEntity p where p.privacy = 'None' and p.id = ?1")
    Optional<PostEntity> findNonPrivateById(Long postId);

    @Query(value = "select p from PostEntity p where p.privacy = 'None' and p.user = ?1")
    List<PostEntity> findAllNonPrivateByUser(UserEntity user);

    @Query(value = "select p from PostEntity p where p.privacy = 'None' and p.user.id = ?1")
    List<PostEntity> findAllNonPrivateByUserId(Long userId);

    boolean existsByIdAndUserId(Long postId, Long userId);
}
