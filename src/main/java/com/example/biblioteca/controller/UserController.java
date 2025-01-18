package com.example.biblioteca.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.dtos.CreateUserDto;
import com.example.biblioteca.dtos.LoginUserDto;
import com.example.biblioteca.dtos.RecoveryJwtTokenDto;
import com.example.biblioteca.entities.User;
import com.example.biblioteca.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // @PostMapping("/login")
    // public ResponseEntity<RecoveryJwtTokenDto> login(@RequestBody LoginUserDto loginUserDto) {
    //     RecoveryJwtTokenDto token = userService.login(loginUserDto);
    //     System.out.println("entrei");
    //     System.out.println( token);
    //     return new ResponseEntity<>(token, HttpStatus.OK);
    // }

   @PostMapping()
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        User user = userService.createUser(createUserDto);
        if (user != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
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
