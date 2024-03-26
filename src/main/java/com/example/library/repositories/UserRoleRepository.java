package com.example.library.repositories;

import com.example.library.models.entities.UserRole;
import com.example.library.models.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByName(RoleEnum roleEnum);
}
