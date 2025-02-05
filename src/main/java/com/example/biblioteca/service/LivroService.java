package com.example.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dto.livro.CreateLivroDto;
import com.example.biblioteca.enums.StatusLivro;
import com.example.biblioteca.model.Livro;
import com.example.biblioteca.repository.LivroRepository;

@Service
public class LivroService {


  @Autowired
  private LivroRepository livroRepository;

  public Livro createLivro(CreateLivroDto createLivroDto) {
    Livro newLivro = Livro.builder()
        .titulo(createLivroDto.titulo())
        .autor(createLivroDto.autor())
        .isbn(createLivroDto.isbn())
        .status(StatusLivro.DISPONIVEL)
        .build();

    try {
      return livroRepository.save(newLivro);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public List<Livro> getLivrosByAutor(String autor) {
    return livroRepository.findByAutor(autor);
  }
}
