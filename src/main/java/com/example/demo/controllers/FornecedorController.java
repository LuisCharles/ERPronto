package com.example.demo.controllers;

import com.example.demo.model.Fornecedor;
import com.example.demo.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository repository;

    @GetMapping
    public List<Fornecedor> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Fornecedor criar(@RequestBody Fornecedor fornecedor) {
        // "nome" (de Pessoa) é obrigatório; se não vier, usamos a razão social.
        if (fornecedor.getNome() == null || fornecedor.getNome().isBlank()) {
            fornecedor.setNome(fornecedor.getRazaoSocial());
        }
        return repository.save(fornecedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizar(@PathVariable Integer id, @RequestBody Fornecedor detalhes) {
        return repository.findById(id).map(fornecedor -> {
            fornecedor.setRazaoSocial(detalhes.getRazaoSocial());
            fornecedor.setNomeFantasia(detalhes.getNomeFantasia());
            if (detalhes.getNome() != null && !detalhes.getNome().isBlank()) fornecedor.setNome(detalhes.getNome());
            fornecedor.setCpfCnpj(detalhes.getCpfCnpj());
            fornecedor.setEmail(detalhes.getEmail());
            fornecedor.setTelefone(detalhes.getTelefone());
            fornecedor.setLogradouro(detalhes.getLogradouro());
            fornecedor.setNumero(detalhes.getNumero());
            fornecedor.setBairro(detalhes.getBairro());
            fornecedor.setCidade(detalhes.getCidade());
            fornecedor.setEstado(detalhes.getEstado());
            fornecedor.setCep(detalhes.getCep());
            fornecedor.setAtivo(detalhes.getAtivo());
            return ResponseEntity.ok(repository.save(fornecedor));
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