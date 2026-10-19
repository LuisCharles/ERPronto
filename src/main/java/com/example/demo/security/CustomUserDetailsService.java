package com.example.demo.security;

import com.example.demo.entities.Perfil;
import com.example.demo.entities.Permissao;
import com.example.demo.entities.Usuario;
import com.example.demo.repositories.PerfilRepositorio;
import com.example.demo.repositories.UsuarioRepositorio;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PerfilRepositorio perfilRepositorio;

    public CustomUserDetailsService(UsuarioRepositorio usuarioRepositorio, PerfilRepositorio perfilRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.perfilRepositorio = perfilRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        if (Boolean.FALSE.equals(usuario.getAtivo())) {
            throw new UsernameNotFoundException("Usuário inativo: " + email);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        // Busca o Perfil e suas Permissões no banco para montar as Roles
        Optional<Perfil> perfilOpt = perfilRepositorio.findById(usuario.getPerfilId());
        if (perfilOpt.isPresent()) {
            Perfil perfil = perfilOpt.get();
            String roleName = perfil.getNome().toUpperCase().startsWith("ROLE_")
                    ? perfil.getNome().toUpperCase()
                    : "ROLE_" + perfil.getNome().toUpperCase();
            authorities.add(new SimpleGrantedAuthority(roleName));

            if (perfil.getPermissoes() != null) {
                for (Permissao perm : perfil.getPermissoes()) {
                    authorities.add(new SimpleGrantedAuthority(perm.getNome().toUpperCase()));
                }
            }
        }

        return new User(usuario.getEmail(), usuario.getSenhaHash(), authorities);
    }
}