package com.example.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.dto.livro.EmprestimoLivro;
import com.example.biblioteca.dto.user.CreateUserDto;
import com.example.biblioteca.dto.user.GetUserDto;
import com.example.biblioteca.dto.user.UserDto;
import com.example.biblioteca.error.ErroResponse;
import com.example.biblioteca.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/emprestimo")
	public ResponseEntity<?> emprestimo(@RequestBody EmprestimoLivro emprestimoLivro) {
		try {
			UserDto user = userService.emprestimo(emprestimoLivro);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(new ErroResponse(e.getMessage(), 422));
		}
	}

	@PostMapping("/devolucao")
	public ResponseEntity<?> devolucao(@RequestBody EmprestimoLivro emprestimoLivro) {
		try {
			UserDto user = userService.devolucao(emprestimoLivro);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(new ErroResponse(e.getMessage(), 422));
		}
	}

	@PostMapping()
	public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto) {
		try {
			UserDto user = userService.createUser(createUserDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
			.body(new ErroResponse(e.getMessage(), 422));
		}
	}

	@GetMapping()
	public ResponseEntity<?> findByEmail(@RequestBody GetUserDto userDto) {
		UserDto user = userService.findDtoByEmail(userDto.email());
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.status(404).body(new ErroResponse("Usuário não encontrado", 404));
		}
	}

}
