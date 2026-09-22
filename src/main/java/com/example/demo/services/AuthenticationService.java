package com.example.demo.services; //Aqui

import com.example.demo.dtos.AuthenticationRequest;
import com.example.demo.dtos.AuthenticationResponse;
import com.example.demo.security.JwtService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService {

    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if ("admin@erp.com".equals(request.getEmail()) && "admin123".equals(request.getPassword())) {
            UserDetails user = new User(request.getEmail(), request.getPassword(), new ArrayList<>());
            String jwtToken = jwtService.generateToken(user);
            return new AuthenticationResponse(jwtToken);
        }
        throw new RuntimeException("Credenciais inválidas");
    }
}