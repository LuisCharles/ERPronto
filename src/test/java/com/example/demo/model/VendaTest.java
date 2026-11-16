package com.example.demo.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class VendaTest {

    private ItemVenda item(String quantidade, String preco, String desconto) {
        ItemVenda i = new ItemVenda();
        i.setProduto(new Produto());
        i.setQuantidade(new BigDecimal(quantidade));
        i.setPrecoUnitario(new BigDecimal(preco));
        i.setDesconto(new BigDecimal(desconto));
        return i;
    }

    @Test
    void calculaSubtotalDescontoETotalAoAdicionarItens() {
        Venda v = new Venda();
        v.adicionarItem(item("2", "10.00", "1.00"));   // 19,00
        v.adicionarItem(item("1", "5.50", "0"));       //  5,50
        v.setDesconto(new BigDecimal("4.50"));
        v.calcularTotais();

        assertEquals(new BigDecimal("24.50"), v.getSubtotal());
        assertEquals(new BigDecimal("20.00"), v.getTotal());
        assertSame(v, v.getItens().get(0).getVenda());
    }

    @Test
    void removerItemRecalculaOsTotais() {
        Venda v = new Venda();
        ItemVenda caro = item("1", "100.00", "0");
        v.adicionarItem(caro);
        v.adicionarItem(item("1", "1.00", "0"));
        v.removerItem(caro);
        assertEquals(new BigDecimal("1.00"), v.getTotal());
        assertNull(caro.getVenda());
    }

    @Test
    void descontoMaiorQueOSubtotalGeraErro() {
        Venda v = new Venda();
        v.adicionarItem(item("1", "10.00", "0"));
        v.setDesconto(new BigDecimal("10.01"));
        assertThrows(IllegalArgumentException.class, v::calcularTotais);
    }

    @Test
    void itemComQuantidadeZeroGeraErro() {
        Venda v = new Venda();
        assertThrows(IllegalArgumentException.class, () -> v.adicionarItem(item("0", "10.00", "0")));
    }

    @Test
    void naoFinalizaVendaSemItens() {
        assertThrows(IllegalStateException.class, new Venda()::finalizar);
    }

    @Test
    void finalizaVendaPendenteSoUmaVez() {
        Venda v = new Venda();
        v.adicionarItem(item("1", "10.00", "0"));
        v.finalizar();
        assertEquals(StatusPedido.FINALIZADA, v.getStatus());
        assertThrows(IllegalStateException.class, v::finalizar);
    }

    @Test
    void cancelaVendaSoUmaVez() {
        Venda v = new Venda();
        v.cancelar();
        assertEquals(StatusPedido.CANCELADA, v.getStatus());
        assertThrows(IllegalStateException.class, v::cancelar);
    }
}
