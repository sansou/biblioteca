package com.example.biblioteca.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.biblioteca.enums.StatusLivro;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Livro")
@Table(name = "livros")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
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
  
  @Builder.Default
  private Integer quantidade = 1;
  
	private String categoria;

	private Date anoPublicacao;

  @Enumerated(EnumType.STRING)
  private StatusLivro status;

  @ManyToMany(mappedBy = "livros")
  @Builder.Default
  @JsonManagedReference
  private List<User> usuarios= new ArrayList<>();

}
