package com.example.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.dto.livro.CreateLivroDto;
import com.example.biblioteca.dto.livro.UpdateLivroDto;
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
    Livro livro = livroService.createLivro(createLivroDto);
    if (livro != null) {
      return ResponseEntity.ok(livro);
    } else {
      return ResponseEntity.unprocessableEntity().body(new ErroResponse("Livro já cadastrado", 422));      
    }
  }


  @PutMapping("/{id}")
  public ResponseEntity<?> atualizarQtdd(@PathVariable Long id, @RequestBody UpdateLivroDto updateLivroDto) {
    System.out.println("Entrei no controller");
    return ResponseEntity.ok(livroService.updateQtdd(id, updateLivroDto));
  }

  @GetMapping("/autor/{autor}")
  public ResponseEntity<List<Livro>> getLivrosByAutor(@PathVariable String autor) {
    return ResponseEntity.ok(livroService.getBooksByAuthor(autor));
  }
}
