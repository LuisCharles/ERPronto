package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Dados comuns a Cliente, Fornecedor e Usuario.
 * JOINED: a tabela "pessoas" guarda estes campos e cada subclasse tem sua
 * própria tabela só com o que é dela (igual ao script ERPronto_Banco_Final.sql).
 */
@Entity
@Table(name = "pessoas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "cpf_cnpj", unique = true, length = 18)
    private String cpfCnpj;

    @Column(unique = true, length = 150)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(length = 120)
    private String logradouro;

    @Column(length = 20)
    private String numero;

    @Column(length = 80)
    private String bairro;

    @Column(length = 80)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Column(length = 9)
    private String cep;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    protected void onCreate() {
        if (this.criadoEm == null) this.criadoEm = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    /** Ex.: "Rua A, 10 - Centro - São Paulo/SP - CEP 01000-000". Partes vazias são puladas. */
    public String getEnderecoCompleto() {
        List<String> partes = new ArrayList<>();
        String ruaENumero = juntar(", ", logradouro, numero);
        if (!ruaENumero.isEmpty()) partes.add(ruaENumero);
        if (temTexto(bairro)) partes.add(bairro);
        String cidadeEUf = juntar("/", cidade, estado);
        if (!cidadeEUf.isEmpty()) partes.add(cidadeEUf);
        if (temTexto(cep)) partes.add("CEP " + cep);
        return String.join(" - ", partes);
    }

    private static String juntar(String separador, String... valores) {
        List<String> preenchidos = new ArrayList<>();
        for (String v : valores) if (temTexto(v)) preenchidos.add(v.trim());
        return String.join(separador, preenchidos);
    }

    private static boolean temTexto(String valor) {
        return valor != null && !valor.isBlank();
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpfCnpj() { return cpfCnpj; }
    public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}
