package com.example.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.dto.user.CreateUserDto;
import com.example.biblioteca.dto.user.GetUserDto;
import com.example.biblioteca.error.ErroResponse;
import com.example.biblioteca.model.User;
import com.example.biblioteca.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping()
	public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto) {
		User user = userService.createUser(createUserDto);
		if (user != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} else {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new ErroResponse("Usuário já cadastrado", 422));
		}
	}

	@GetMapping()
	public ResponseEntity<?> findByEmail(@RequestBody GetUserDto userDto) {
		User user = userService.findByEmail(userDto.email());
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.status(404).body(new ErroResponse("Usuário não encontrado", 404));
		}
	}

	@GetMapping("/test/customer")
	public ResponseEntity<String> getCustomerAuthenticationTest() {
		return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
	}

	@GetMapping("/test/administrator")
	public ResponseEntity<String> getAdminAuthenticationTest() {
		return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
	}

}
