package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dto.livro.EmprestimoLivro;
import com.example.biblioteca.dto.user.CreateUserDto;
import com.example.biblioteca.dto.user.UserDto;
import com.example.biblioteca.enums.StatusLivro;
import com.example.biblioteca.enums.UserRoles;
import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.Role;
import com.example.biblioteca.model.User;
import com.example.biblioteca.repository.RoleRepository;
import com.example.biblioteca.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private LivroService livroService;

  // Método responsável por criar um usuário
  public UserDto createUser(CreateUserDto createUserDto) {
    // Verifica se a permissão fornecida existe
    Role role = validateRole(createUserDto.role());

    // Cria um novo usuário com os dados fornecidos
    User newUser = User.builder()
        .email(createUserDto.email())// Codifica a senha do usuário com o algoritmo bcrypt
        .password(createUserDto.password())
        .name(createUserDto.name())// Atribui ao usuário uma permissão específica
        .roles(role != null ? List.of(role) : List.of(Role.builder().name(createUserDto.role()).build()))
        .build();

    // Salva o novo usuário no banco de dados

    userRepository.findByEmail(createUserDto.email()).ifPresent(user -> {
      throw new ServiceException("Usuário já cadastrado");
    });
    User userSave = userRepository.save(newUser);
    return new UserDto(userSave);

  }

  private Role validateRole(UserRoles role) {
    Role userRole = null;
    if (roleExists(role)) {
      userRole = roleRepository.findByName(role);
    }
    return userRole;
  }

  private boolean roleExists(UserRoles role) {
    return roleRepository.existsByName(role) ? true : false;
  }

  public User findByEmail(String email) {
    Optional<User> opt = userRepository.findByEmail(email);
    System.out.println(opt.isPresent());
    if (!opt.isPresent()) {
      throw new ServiceException("Usuário não encontrado");
    }
    return opt.get();
  }

  public UserDto findDtoByEmail(String email) {
    return new UserDto(findByEmail(email));
  }

  public UserDto emprestimo(EmprestimoLivro emprestimoLivro) {
    List<Livro> livros = livroService.findByIsbns(emprestimoLivro.isbns());
    User user = this.findByEmail(emprestimoLivro.email());

    livros.forEach(l -> {
      if (l.getUsuarios().size() >= l.getQuantidade()) {
        throw new ServiceException("Livro " + l.getTitulo() + " não disponível para emprestimo");
      }
      user.getLivros().add(l);
      l.getUsuarios().add(user);
    });

    User userSave = userRepository.save(user);
    return new UserDto(userSave);
  }

  public UserDto devolucao(EmprestimoLivro emprestimoLivro) {
    List<Livro> livros = livroService.findByIsbns(emprestimoLivro.isbns());
    User user = this.findByEmail(emprestimoLivro.email());

    livros.forEach(l -> {
      if(l.getUsuarios().size() == 0){
        throw new ServiceException("Livro " + l.getTitulo() + " não foi emprestado");
      }
      user.getLivros().remove(l);
      l.getUsuarios().remove(user);
    });

    User userSave = userRepository.save(user);
    return new UserDto(userSave);
  }

}
