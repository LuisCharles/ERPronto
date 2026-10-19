package com.example.demo.security;

import com.example.demo.entities.Perfil;
import com.example.demo.entities.Usuario;
import com.example.demo.repositories.PerfilRepositorio;
import com.example.demo.repositories.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PerfilRepositorio perfilRepositorio;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            UsuarioRepositorio usuarioRepositorio,
            PerfilRepositorio perfilRepositorio,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.perfilRepositorio = perfilRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Perfil perfilAdmin;
        if (perfilRepositorio.count() == 0) {
            Perfil p = new Perfil();
            p.setNome("ADMIN");
            p.setDescricao("Administrador do Sistema");
            perfilAdmin = perfilRepositorio.save(p);
        } else {
            perfilAdmin = perfilRepositorio.findAll().get(0);
        }

        if (!usuarioRepositorio.existsByEmail("admin@erp.com")) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@erp.com");
            admin.setSenhaHash(passwordEncoder.encode("admin123"));
            admin.setPerfilId(perfilAdmin.getId());
            admin.setAtivo(true);
            usuarioRepositorio.save(admin);
            System.out.println(">>> Usuário admin@erp.com criado no banco com sucesso!");
        }
    }
}