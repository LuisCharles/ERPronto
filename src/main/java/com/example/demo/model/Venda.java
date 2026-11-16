package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data_venda", nullable = false)
    private LocalDateTime dataVenda;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPedido status = StatusPedido.PENDENTE;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    // Composição: os itens só existem dentro da venda (salvos e apagados junto com ela).
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenda> itens = new ArrayList<>();

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
        if (this.dataVenda == null) this.dataVenda = this.criadoEm;
        if (this.status == null) this.status = StatusPedido.PENDENTE;
    }

    @PreUpdate
    protected void onUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public void adicionarItem(ItemVenda item) {
        item.setVenda(this);
        itens.add(item);
        calcularTotais();
    }

    public void removerItem(ItemVenda item) {
        itens.remove(item);
        item.setVenda(null);
        calcularTotais();
    }

    public void calcularTotais() {
        BigDecimal soma = BigDecimal.ZERO;
        for (ItemVenda item : itens) soma = soma.add(item.calcularSubtotal());
        BigDecimal descontoAtual = desconto != null ? desconto : BigDecimal.ZERO;
        if (descontoAtual.signum() < 0 || descontoAtual.compareTo(soma) > 0) {
            throw new IllegalArgumentException("O desconto deve ficar entre zero e o subtotal da venda (" + soma + ").");
        }
        this.subtotal = soma;
        this.desconto = descontoAtual;
        this.total = soma.subtract(descontoAtual);
    }

    public void finalizar() {
        if (status != StatusPedido.PENDENTE) {
            throw new IllegalStateException("Só é possível finalizar uma venda pendente. Situação atual: " + status + ".");
        }
        if (itens.isEmpty()) {
            throw new IllegalStateException("Não é possível finalizar uma venda sem itens.");
        }
        calcularTotais();
        this.status = StatusPedido.FINALIZADA;
    }

    public void cancelar() {
        if (status == StatusPedido.CANCELADA) {
            throw new IllegalStateException("Esta venda já está cancelada.");
        }
        this.status = StatusPedido.CANCELADA;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public LocalDateTime getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDateTime dataVenda) { this.dataVenda = dataVenda; }
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getDesconto() { return desconto; }
    public void setDesconto(BigDecimal desconto) { this.desconto = desconto; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public List<ItemVenda> getItens() { return itens; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}
