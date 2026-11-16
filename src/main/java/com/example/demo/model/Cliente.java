package com.example.demo.model;

import jakarta.persistence.*;

/** Pessoa que compra da empresa. Os dados de contato e endereço vêm de Pessoa. */
@Entity
@Table(name = "clientes")
public class Cliente extends Pessoa {

    @Column(nullable = false)
    private Boolean ativo = true;

    public void ativar() { this.ativo = true; }

    /** Não apagamos clientes com histórico de vendas: só desativamos. */
    public void desativar() { this.ativo = false; }

    public Boolean getAtivo() { return ativo; }
    // A coluna não aceita vazio: sem valor informado, fica ativo.
    public void setAtivo(Boolean ativo) { this.ativo = ativo != null ? ativo : true; }
}
