package com.example.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.dto.livro.CreateLivroDto;
import com.example.biblioteca.dto.livro.EmprestimoLivro;
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

  // @PutMapping("/{id}/devolucao")
  // public ResponseEntity<?> devolucao(@PathVariable Long id, @RequestBody String userEmail) {
  //   Livro livro = livroService.emprestimo(id, userEmail);
  //   if (livro != null) {
  //     return ResponseEntity.ok(livro);
  //   } else {
  //     return ResponseEntity.status(404).body(new ErroResponse("Livro ou Usuário não encontrado", 404));
  //   }
  // }

  @GetMapping("/autor/{autor}")
  public ResponseEntity<?> getLivrosByAutor(@PathVariable String autor) {
    List<Livro> livros = livroService.getBooksByAuthor(autor);
    if(!livros.isEmpty()){
      return ResponseEntity.ok(livroService.getBooksByAuthor(autor));
    } else {
      return ResponseEntity.status(404).body(new ErroResponse("Autor não encontrado", 404));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteLivro(@PathVariable Long id) {
    Boolean livroDeletado = livroService.deleteLivro(id);
    if (livroDeletado) {
      return ResponseEntity.ok(livroService.deleteLivro(id));
    } else {
      return ResponseEntity.status(404).body(new ErroResponse("Livro não encontrado", 404));
    }
  }
}
