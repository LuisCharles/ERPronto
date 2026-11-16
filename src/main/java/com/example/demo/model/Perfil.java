package com.example.demo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "perfis")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(length = 150)
    private String descricao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "perfil_permissao",
        joinColumns = @JoinColumn(name = "perfil_id"),
        inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private Set<Permissao> permissoes = new HashSet<>();

    public void adicionarPermissao(Permissao permissao) {
        permissoes.add(permissao);
    }

    public void removerPermissao(Permissao permissao) {
        permissoes.remove(permissao);
    }

    /** Compara ignorando maiúsculas/minúsculas: "vendas_criar" == "VENDAS_CRIAR". */
    public boolean possuiPermissao(String nome) {
        if (nome == null) return false;
        return permissoes.stream().anyMatch(p -> nome.equalsIgnoreCase(p.getNome()));
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Set<Permissao> getPermissoes() { return permissoes; }
    public void setPermissoes(Set<Permissao> permissoes) { this.permissoes = permissoes; }
}
