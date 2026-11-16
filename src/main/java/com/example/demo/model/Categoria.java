package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(length = 150)
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo = true;

    public void ativar() { this.ativo = true; }

    /** Categoria com produtos não é apagada: só desativada. */
    public void desativar() { this.ativo = false; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Boolean getAtivo() { return ativo; }
    // A coluna não aceita vazio: sem valor informado, fica ativa.
    public void setAtivo(Boolean ativo) { this.ativo = ativo != null ? ativo : true; }
}
