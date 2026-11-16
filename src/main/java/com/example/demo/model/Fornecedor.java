package com.example.demo.model;

import jakarta.persistence.*;

/** Empresa da qual compramos mercadorias. O CNPJ fica em Pessoa.cpfCnpj. */
@Entity
@Table(name = "fornecedores")
public class Fornecedor extends Pessoa {

    @Column(name = "razao_social", nullable = false, length = 120)
    private String razaoSocial;

    @Column(name = "nome_fantasia", length = 120)
    private String nomeFantasia;

    @Column(nullable = false)
    private Boolean ativo = true;

    public void ativar() { this.ativo = true; }

    /** Não apagamos fornecedores com histórico de compras: só desativamos. */
    public void desativar() { this.ativo = false; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
    public Boolean getAtivo() { return ativo; }
    // A coluna não aceita vazio: sem valor informado, fica ativo.
    public void setAtivo(Boolean ativo) { this.ativo = ativo != null ? ativo : true; }
}
