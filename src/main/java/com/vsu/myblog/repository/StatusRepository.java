package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {

    StatusEntity findByStatus(String status);
}
