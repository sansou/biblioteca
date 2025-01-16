package com.example.biblioteca.dtos;

import com.example.biblioteca.enums.UserRoles;

public record CreateUserDto(String email, String password, String name, UserRoles role) {
}
