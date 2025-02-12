package com.example.biblioteca.dto.livro;

import java.util.List;

public record EmprestimoLivro(String email, List<String> isbns) {

}
