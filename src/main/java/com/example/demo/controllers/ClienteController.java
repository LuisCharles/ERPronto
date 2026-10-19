package com.example.demo.controllers;

import com.example.demo.entities.Cliente;
import com.example.demo.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Cliente criar(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Integer id, @RequestBody Cliente detalhes) {
        return repository.findById(id).map(cliente -> {
            cliente.setNome(detalhes.getNome());
            cliente.setCpfCnpj(detalhes.getCpfCnpj());
            cliente.setEmail(detalhes.getEmail());
            cliente.setTelefone(detalhes.getTelefone());
            cliente.setLogradouro(detalhes.getLogradouro());
            cliente.setNumero(detalhes.getNumero());
            cliente.setBairro(detalhes.getBairro());
            cliente.setCidade(detalhes.getCidade());
            cliente.setEstado(detalhes.getEstado());
            cliente.setCep(detalhes.getCep());
            cliente.setAtivo(detalhes.getAtivo());
            return ResponseEntity.ok(repository.save(cliente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}