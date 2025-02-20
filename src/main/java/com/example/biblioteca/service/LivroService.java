package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
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
    List<Livro> livros = livroRepository.findByAutor(autor);
    if (livros.isEmpty()) {
      throw new ServiceException("Não há nenhum livro cadastrado para o autor informado");
    }
    return livros;
  }

  public void deleteLivro(Long id) {
    Livro livro = this.findById(id);
    livroRepository.delete(livro);
  }

  private Livro findById(Long id) {
    Optional<Livro> opt = livroRepository.findById(id);
    if (!opt.isPresent()) {
      throw new ServiceException("Livro não encontrado");
    }
    return opt.get();

  }

  public List<Livro> findByIsbns(List<String> isbns) {
    List<Livro> livros =livroRepository.findByIsbns(isbns);
    if (livros.isEmpty()) {
      throw new ServiceException("Não há nenhum livro cadastrado para os ISBNs informados");
    }
    return livros;
  }

}
