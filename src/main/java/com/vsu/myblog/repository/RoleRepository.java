package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByRole(String role);
}
