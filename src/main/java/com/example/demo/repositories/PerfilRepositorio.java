package com.example.demo.repositories;

import com.example.demo.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepositorio extends JpaRepository<Perfil, Integer> {
}