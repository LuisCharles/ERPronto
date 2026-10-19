package com.example.demo.dtos;

public class UsuarioRequestDTO {
    private String nome;
    private String email;
    private String senha;
    private Integer perfilId;
    private Boolean ativo;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Integer getPerfilId() { return perfilId; }
    public void setPerfilId(Integer perfilId) { this.perfilId = perfilId; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}