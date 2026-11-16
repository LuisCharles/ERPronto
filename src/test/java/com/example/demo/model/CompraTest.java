package com.example.demo.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CompraTest {

    private ItemCompra item(String quantidade, String preco) {
        ItemCompra i = new ItemCompra();
        i.setProduto(new Produto());
        i.setQuantidade(new BigDecimal(quantidade));
        i.setPrecoUnitario(new BigDecimal(preco));
        return i;
    }

    @Test
    void calculaTotaisEFinaliza() {
        Compra c = new Compra();
        c.adicionarItem(item("3", "2.50"));   // 7,50
        c.adicionarItem(item("1", "12.00"));  // 12,00
        c.setDesconto(new BigDecimal("1.50"));
        c.finalizar();

        assertEquals(new BigDecimal("19.50"), c.getSubtotal());
        assertEquals(new BigDecimal("18.00"), c.getTotal());
        assertEquals(StatusPedido.FINALIZADA, c.getStatus());
    }

    @Test
    void naoFinalizaCompraSemItensNemCompraCancelada() {
        assertThrows(IllegalStateException.class, new Compra()::finalizar);

        Compra cancelada = new Compra();
        cancelada.adicionarItem(item("1", "1.00"));
        cancelada.cancelar();
        assertThrows(IllegalStateException.class, cancelada::finalizar);
    }

    @Test
    void precoNegativoGeraErro() {
        assertThrows(IllegalArgumentException.class, () -> new Compra().adicionarItem(item("1", "-1.00")));
    }
}
