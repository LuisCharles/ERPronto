package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data_compra", nullable = false)
    private LocalDateTime dataCompra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPedido status = StatusPedido.PENDENTE;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    // Composição: os itens só existem dentro da compra (salvos e apagados junto com ela).
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCompra> itens = new ArrayList<>();

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
        if (this.dataCompra == null) this.dataCompra = this.criadoEm;
        if (this.status == null) this.status = StatusPedido.PENDENTE;
    }

    @PreUpdate
    protected void onUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public void adicionarItem(ItemCompra item) {
        item.setCompra(this);
        itens.add(item);
        calcularTotais();
    }

    public void removerItem(ItemCompra item) {
        itens.remove(item);
        item.setCompra(null);
        calcularTotais();
    }

    public void calcularTotais() {
        BigDecimal soma = BigDecimal.ZERO;
        for (ItemCompra item : itens) soma = soma.add(item.calcularSubtotal());
        BigDecimal descontoAtual = desconto != null ? desconto : BigDecimal.ZERO;
        if (descontoAtual.signum() < 0 || descontoAtual.compareTo(soma) > 0) {
            throw new IllegalArgumentException("O desconto deve ficar entre zero e o subtotal da compra (" + soma + ").");
        }
        this.subtotal = soma;
        this.desconto = descontoAtual;
        this.total = soma.subtract(descontoAtual);
    }

    public void finalizar() {
        if (status != StatusPedido.PENDENTE) {
            throw new IllegalStateException("Só é possível finalizar uma compra pendente. Situação atual: " + status + ".");
        }
        if (itens.isEmpty()) {
            throw new IllegalStateException("Não é possível finalizar uma compra sem itens.");
        }
        calcularTotais();
        this.status = StatusPedido.FINALIZADA;
    }

    public void cancelar() {
        if (status == StatusPedido.CANCELADA) {
            throw new IllegalStateException("Esta compra já está cancelada.");
        }
        this.status = StatusPedido.CANCELADA;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Fornecedor getFornecedor() { return fornecedor; }
    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public LocalDateTime getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDateTime dataCompra) { this.dataCompra = dataCompra; }
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getDesconto() { return desconto; }
    public void setDesconto(BigDecimal desconto) { this.desconto = desconto; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public List<ItemCompra> getItens() { return itens; }
    public void setItens(List<ItemCompra> itens) { this.itens = itens; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}
