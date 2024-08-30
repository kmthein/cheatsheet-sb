package com.cheatsheet.controller;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.User;
import com.cheatsheet.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws URISyntaxException {
        ResponseDTO res = service.register(user);
        if(!res.getStatus().equals("201")) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        URI uri = new URI("/api/login");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        return service.login(user);
    }
}
