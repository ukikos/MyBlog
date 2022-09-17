package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {

    Optional<StatusEntity> findByStatus(String status);
}
