package com.example.demo.controllers;

import com.example.demo.dtos.UsuarioRequestDTO;
import com.example.demo.entities.Usuario;
import com.example.demo.repositories.UsuarioRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioRepositorio.findAll());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequestDTO dto) {
        if (usuarioRepositorio.existsByEmail(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado.");
        }

        Usuario novo = new Usuario();
        novo.setNome(dto.getNome());
        novo.setEmail(dto.getEmail());
        novo.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        novo.setPerfilId(dto.getPerfilId() != null ? dto.getPerfilId() : 1);
        novo.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        Usuario salvo = usuarioRepositorio.save(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}