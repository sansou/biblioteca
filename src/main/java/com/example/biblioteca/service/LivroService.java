package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dto.livro.CreateLivroDto;
import com.example.biblioteca.dto.livro.UpdateLivroDto;
import com.example.biblioteca.enums.StatusLivro;
import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.User;
import com.example.biblioteca.repository.LivroRepository;

@Service
public class LivroService {

  @Autowired
  private LivroRepository livroRepository;

  @Autowired
  private UserService userService;

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
      return null;
    }
  }

  public List<Livro> getBooksByAuthor(String autor) {
    return livroRepository.findByAutor(autor);
  }

  public Boolean deleteLivro(Long id) {
    Livro livro = this.findById(id);
    if (livro == null) {
      return false;      
    } else {
      livroRepository.delete(livro);
      return true;
    }
  }

  public Livro findById(Long id) {
    Optional<Livro> opt = livroRepository.findById(id);
    if (!opt.isPresent()) {
      return null;
    }
    return opt.get();
  }

  public Livro emprestimo(Long id, String userEmail) {
    Livro livro = this.findById(id);
    User user = userService.findByEmail(userEmail);
    System.out.println(userEmail +"/n");
    System.out.println(livro);
    System.out.println(user);
    if (livro == null || user == null) {
      return null;
    }
    livro.setStatus(StatusLivro.EMPRESTADO);
    return livroRepository.save(livro);
  }

  public Livro devolucao(Long id, String userEmail) {
    Livro livro = this.findById(id);
    User user = userService.findByEmail(userEmail);
    if (livro == null || user == null) {
      return null;
    }
    livro.setStatus(StatusLivro.DISPONIVEL);
    return livroRepository.save(livro);
  }
}
