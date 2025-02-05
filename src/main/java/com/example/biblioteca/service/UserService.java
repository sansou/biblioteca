package com.example.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dto.user.CreateUserDto;
import com.example.biblioteca.model.Role;
import com.example.biblioteca.model.User;
import com.example.biblioteca.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  // Método responsável por autenticar um usuário e retornar um token JWT
  // public RecoveryJwtTokenDto login(LoginUserDto loginUserDto) {
  //   // Cria um objeto de autenticação com o email e a senha do usuário
  //   UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
  //       loginUserDto.email(), loginUserDto.password());

  //   // Autentica o usuário com as credenciais fornecidas
  //   Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
  // }

  // Método responsável por criar um usuário
  public User createUser(CreateUserDto createUserDto) throws Exception{

    // Cria um novo usuário com os dados fornecidos
    User newUser = User.builder()
        .email(createUserDto.email())
        // Codifica a senha do usuário com o algoritmo bcrypt
        .password(createUserDto.password())
        .name(createUserDto.name())
        // Atribui ao usuário uma permissão específica
        .roles(List.of(Role.builder().name(createUserDto.role()).build()))
        .build();

    // Salva o novo usuário no banco de dados
    try {
      userRepository.findByEmail(createUserDto.email()).ifPresent(user -> {
        throw new RuntimeException("Usuário já cadastrado");
      });
      return userRepository.save(newUser);

    } catch (Exception e) {
      return null;
    }
  }
}
