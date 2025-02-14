package com.example.biblioteca.dto.user;

import java.util.List;

import com.example.biblioteca.model.User;

public record UserDto(String name, String email, List<LivroDto> livros) {
  public UserDto(User user){
    this(user.getName(), user.getEmail(), user.getLivros().stream()
    .map(livro -> new LivroDto(livro.getTitulo(), livro.getAutor(), livro.getIsbn(), livro.getCategoria())).toList());
  }
}

