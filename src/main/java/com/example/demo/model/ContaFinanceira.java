package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Conta a pagar ou a receber. Pode vir de uma venda, de uma compra (nunca das duas) ou ser manual. */
@Entity
@Table(name = "contas_financeiras")
public class ContaFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoConta tipo;

    @Column(length = 150)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusConta status = StatusConta.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    protected void onCreate() {
        validarOrigem();
        this.criadoEm = LocalDateTime.now();
        if (this.status == null) this.status = StatusConta.PENDENTE;
    }

    @PreUpdate
    protected void onUpdate() {
        validarOrigem();
        this.atualizadoEm = LocalDateTime.now();
    }

    public void registrarPagamento(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("Informe a data do pagamento.");
        }
        if (status == StatusConta.PAGO || status == StatusConta.CANCELADO) {
            throw new IllegalStateException("Não é possível pagar uma conta com situação " + status + ".");
        }
        this.dataPagamento = data;
        this.status = StatusConta.PAGO;
    }

    public void cancelar() {
        if (status == StatusConta.PAGO) {
            throw new IllegalStateException("Uma conta já paga não pode ser cancelada.");
        }
        this.status = StatusConta.CANCELADO;
    }

    /** Pendente (ou já marcada como vencida) e com vencimento antes de hoje. */
    public boolean isVencida() {
        boolean emAberto = status == StatusConta.PENDENTE || status == StatusConta.VENCIDO;
        return emAberto && dataVencimento != null && dataVencimento.isBefore(LocalDate.now());
    }

    private void validarOrigem() {
        if (venda != null && compra != null) {
            throw new IllegalStateException("Uma conta não pode estar ligada a uma venda e a uma compra ao mesmo tempo.");
        }
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public TipoConta getTipo() { return tipo; }
    public void setTipo(TipoConta tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }
    public StatusConta getStatus() { return status; }
    public void setStatus(StatusConta status) { this.status = status; }
    public Venda getVenda() { return venda; }
    public void setVenda(Venda venda) { this.venda = venda; }
    public Compra getCompra() { return compra; }
    public void setCompra(Compra compra) { this.compra = compra; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}
