package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dto.livro.CreateLivroDto;
import com.example.biblioteca.model.Livro;
import com.example.biblioteca.repository.LivroRepository;

@Service
public class LivroService {

  @Autowired
  private LivroRepository livroRepository;

  public Livro createLivro(CreateLivroDto createLivroDto) {
    Livro newLivro = new Livro(createLivroDto); 
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

  private Livro findById(Long id) {
    Optional<Livro> opt = livroRepository.findById(id);
    if (!opt.isPresent()) {
      return null;
    }
    return opt.get();

  }

  public List<Livro> findByIsbns(List<String> isbns) {
    return livroRepository.findByIsbns(isbns);
  }

  // public Livro devolucao(Long id, String userEmail) {
  //   Livro livro = this.findById(id);
  //   User user = userService.findByEmail(userEmail);
  //   if (livro == null || user == null) {
  //     return null;
  //   }
  //   livro.setStatus(StatusLivro.DISPONIVEL);
  //   return userService.save(livro);
  // }
}
