package com.example.library.services;

import com.example.library.models.entities.UserRole;
import com.example.library.models.enums.RoleEnum;
import com.example.library.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole getRole(String name) {
        return this.userRoleRepository.findByName(RoleEnum.valueOf(name));
    }
}