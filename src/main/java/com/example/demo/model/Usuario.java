package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/** Pessoa que acessa o sistema. O e-mail (herdado de Pessoa) é o login. */
@Entity
@Table(name = "usuarios")
public class Usuario extends Pessoa {

    @JsonIgnore
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    // Na resposta da API vai só o "perfilId" (ver getPerfilId), que é o que o frontend usa.
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @Column(nullable = false)
    private Boolean ativo = true;

    public boolean possuiPermissao(String nome) {
        return perfil != null && perfil.possuiPermissao(nome);
    }

    public void ativar() { this.ativo = true; }

    /** Usuário desativado não consegue mais fazer login. */
    public void desativar() { this.ativo = false; }

    public Integer getPerfilId() { return perfil != null ? perfil.getId() : null; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
    public Boolean getAtivo() { return ativo; }
    // A coluna não aceita vazio: sem valor informado, fica ativo.
    public void setAtivo(Boolean ativo) { this.ativo = ativo != null ? ativo : true; }
}
