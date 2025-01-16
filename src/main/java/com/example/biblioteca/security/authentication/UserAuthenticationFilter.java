package com.example.biblioteca.security.authentication;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.biblioteca.entities.User;
import com.example.biblioteca.repositories.UserRepository;
import com.example.biblioteca.security.config.SecurityConfiguration;
import com.example.biblioteca.security.userdetails.UserDetailsImpl;

import java.io.IOException;
import java.util.Arrays;

public class UserAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private JwtTokenService jwtTokenService;

  @Autowired
  private UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // verifica se o endpoint requer autenticação antes de processar a requisição
    if (checkIfEndpointIsNotPublic(request)) {
      // recupera o token do header
      String token = recoveryToken(request); // verifica se o token é válido
      if (token != null) {
        String subject = jwtTokenService.getSubjectFromToken(token); // Obtém o assunto (neste caso, o nome de usuário) do token
        User user = userRepository.findByEmail(subject).get(); // Busca o usuário pelo email (que é o assunto do token)
        UserDetailsImpl userDetails = new UserDetailsImpl(user); // Cria um UserDetails com o usuário encontrado

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null, userDetails.getAuthorities());
        
        // define o objeto de autenticação no contexto de segurança
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
  }

  private String recoveryToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
  }

  private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
    // TODO Auto-generated method stub
   String requestURI = request.getRequestURI();
        return !Arrays.asList(SecurityConfiguration.PUBLIC_MATCHERS).contains(requestURI);
  }

}
