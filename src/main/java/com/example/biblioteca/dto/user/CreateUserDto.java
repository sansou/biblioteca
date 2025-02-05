package com.example.biblioteca.dto.user;

import com.example.biblioteca.enums.UserRoles;

public record CreateUserDto(String email, String password, String name, UserRoles role) {
}
