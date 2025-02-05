package com.example.biblioteca.dto.livro;

import java.util.Date;

import com.example.biblioteca.enums.StatusLivro;

public record CreateLivroDto(String titulo, String autor, String isbn, String categoria, Date anoPublicacao, StatusLivro status) {

}
