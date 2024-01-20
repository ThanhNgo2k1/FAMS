package com.group3.fams.repositories;

import com.group3.fams.entity.UserPermission;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Integer> {

    Optional<UserPermission> findById(Integer permissionId);

    Page<UserPermission> findAll(Pageable pageable);
}
