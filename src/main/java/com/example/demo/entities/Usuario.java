
package com.example.demo.entities; //Altera aqui AbuDabhi
import jakarta.persistence.*;
import java.time.OffsetDateTime;

//@entity quer dizer "Essa classe representa uma tabela no BD" --Luiz
@Entity

//@Table = especifíca a tabela (anota ai Leozin)
// Não tem ; nas linhas com @
// Basicamente temos que representar a tabela no códio
@Table(name = "Usuarios")
public class Usuario{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco gera o ID
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(name = "perfil_id", nullable = false)
    private Integer perfilId;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "atualizado_em")
    private OffsetDateTime atualizadoEm;

    @Column(name = "criado_em", nullable = false)
    private OffsetDateTime criadoEm;

    // Getters e Setters igual a luciene ensinou
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getSenhaHash(){
        return senhaHash;
    }

    public setSenhaHash(String senhaHash){
        this.senhaHash = senhaHash;
    }

    public Integer getPerfilId(){
        return perfilId;
    }

    public void setPerfilId(Integer perfilId){
        this.perfilId = perfilId;
    }

    public Boolean getAtivo(){
        return ativo;
    }

    public void setAtivo(Boolean ativo){
        this.ativo = ativo;
    }

    public OffsetDateTime getAtualizadoEm(){
        return atualizadoEm;
    }

    public void setAtualizadoEm(OffsetDateTime atualizadoEm){
        this.atualizadoEm = atualizadoEm;
    }

    public OffsetDateTime getCriadoEm(){
        return criadoEm;
    }

    public void setCriadoEm(OffsetDateTime criadoEm){
        this.criadoEm = criadoEm;
    }
}