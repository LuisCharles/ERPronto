package com.example.demo.repositories;

import com.example.demo.entities.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepositorio extends JpaRepository<Permissao, Integer> {
}