package com.example.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.dto.livro.CreateLivroDto;
import com.example.biblioteca.error.ErroResponse;
import com.example.biblioteca.model.Livro;
import com.example.biblioteca.service.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

  @Autowired
  private LivroService livroService;

  @PostMapping()
  public ResponseEntity<?> createLivro(@RequestBody CreateLivroDto createLivroDto) {
    try {
      Livro livro = livroService.createLivro(createLivroDto);
      return ResponseEntity.ok(livro);
    } catch (Exception e) {
      return ResponseEntity.unprocessableEntity().body(new ErroResponse("Livro já cadastrado", 422));      
    }
   
  }

  @GetMapping("/autor/{autor}")
  public ResponseEntity<?> getLivrosByAutor(@PathVariable String autor) {
    try {
      List<Livro> livros = livroService.getBooksByAuthor(autor);
      return ResponseEntity.ok(livros);
    } catch (Exception e) {
      return ResponseEntity.status(404).body(new ErroResponse(e.getMessage(), 404));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteLivro(@PathVariable Long id) {
    try {
      livroService.deleteLivro(id);
      return ResponseEntity.ok().build();
      
    } catch (Exception e) {
      return ResponseEntity.status(404).body(new ErroResponse(e.getMessage(), 404));
    }
  }
}
