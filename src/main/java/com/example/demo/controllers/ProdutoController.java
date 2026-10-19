package com.example.demo.controllers;

import com.example.demo.entities.Produto;
import com.example.demo.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Produto criar(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Integer id, @RequestBody Produto detalhes) {
        return repository.findById(id).map(produto -> {
            produto.setCategoria(detalhes.getCategoria());
            produto.setNome(detalhes.getNome());
            produto.setCodigo(detalhes.getCodigo());
            produto.setDescricao(detalhes.getDescricao());
            produto.setPrecoCusto(detalhes.getPrecoCusto());
            produto.setPrecoVenda(detalhes.getPrecoVenda());
            produto.setEstoqueAtual(detalhes.getEstoqueAtual());
            produto.setEstoqueMinimo(detalhes.getEstoqueMinimo());
            produto.setAtivo(detalhes.getAtivo());
            return ResponseEntity.ok(repository.save(produto));
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