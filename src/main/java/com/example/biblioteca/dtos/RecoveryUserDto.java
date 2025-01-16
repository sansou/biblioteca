package com.example.biblioteca.dtos;

import java.util.List;

import com.example.biblioteca.enums.UserRoles;

public record RecoveryUserDto(Long id, String email, String name, List<UserRoles> role) {
  
}
