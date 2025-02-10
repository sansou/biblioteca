package com.example.biblioteca.model;

import java.util.Date;

import com.example.biblioteca.enums.StatusLivro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Livro")
@Table(name = "livros")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Livro {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String titulo;
  
  @Column(nullable = false)
	private String autor;
  
  
  @Column(unique = true) 
	private String isbn;

	private String categoria;

	private Date anoPublicacao;

  @Enumerated(EnumType.STRING)
  private StatusLivro status;

}
