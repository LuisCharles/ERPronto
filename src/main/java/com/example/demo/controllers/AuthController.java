//Altera aqui

package com.example.demo.controllers;

import com.example.demo.dtos.AuthenticationRequest;
import com.example.demo.dtos.AuthenticationResponse;
import com.example.demo.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService service;

    public AuthController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).build(); // 401 Unauthorized para senha errada
        }
    }
}