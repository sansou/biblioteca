package com.example.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.biblioteca.enums.UserRoles;
import com.example.biblioteca.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(UserRoles name);

}
