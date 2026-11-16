package com.example.demo.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    private Produto produtoCom(String estoque, String minimo) {
        Produto p = new Produto();
        p.setNome("Café");
        p.setEstoqueAtual(new BigDecimal(estoque));
        p.setEstoqueMinimo(new BigDecimal(minimo));
        return p;
    }

    @Test
    void adicionarERemoverEstoqueAtualizaAQuantidade() {
        Produto p = produtoCom("10", "2");
        p.adicionarEstoque(new BigDecimal("5"));
        p.removerEstoque(new BigDecimal("3"));
        assertEquals(0, new BigDecimal("12").compareTo(p.getEstoqueAtual()));
    }

    @Test
    void removerMaisDoQueOEstoqueGeraErroEMantemOEstoque() {
        Produto p = produtoCom("2", "0");
        assertThrows(IllegalStateException.class, () -> p.removerEstoque(new BigDecimal("3")));
        assertEquals(0, new BigDecimal("2").compareTo(p.getEstoqueAtual()));
    }

    @Test
    void quantidadeZeroNegativaOuVaziaGeraErro() {
        Produto p = produtoCom("10", "0");
        assertThrows(IllegalArgumentException.class, () -> p.adicionarEstoque(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> p.removerEstoque(new BigDecimal("-1")));
        assertThrows(IllegalArgumentException.class, () -> p.adicionarEstoque(null));
    }

    @Test
    void estoqueBaixoQuandoChegaNoMinimo() {
        assertTrue(produtoCom("5", "5").isEstoqueBaixo());
        assertTrue(produtoCom("4", "5").isEstoqueBaixo());
        assertFalse(produtoCom("6", "5").isEstoqueBaixo());
    }

    @Test
    void margemDeLucroEmPorcentagem() {
        Produto p = produtoCom("0", "0");
        p.setPrecoCusto(new BigDecimal("6.00"));
        p.setPrecoVenda(new BigDecimal("10.00"));
        assertEquals(new BigDecimal("40.00"), p.calcularMargemLucro());
    }

    @Test
    void margemDeLucroEZeroQuandoNaoTemPrecoDeVenda() {
        Produto p = produtoCom("0", "0");
        p.setPrecoVenda(BigDecimal.ZERO);
        assertEquals(new BigDecimal("0.00"), p.calcularMargemLucro());
    }
}
