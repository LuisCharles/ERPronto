package com.example.demo.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MovimentacaoEstoqueTest {

    private Produto produtoComEstoque(String estoque) {
        Produto p = new Produto();
        p.setNome("Arroz");
        p.setEstoqueAtual(new BigDecimal(estoque));
        return p;
    }

    private MovimentacaoEstoque movimentacao(Produto p, TipoMovimentacao tipo, String quantidade) {
        MovimentacaoEstoque m = new MovimentacaoEstoque();
        m.setProduto(p);
        m.setTipo(tipo);
        m.setQuantidade(new BigDecimal(quantidade));
        return m;
    }

    @Test
    void entradaSomaSaidaSubtraiEAjusteDefine() {
        Produto p = produtoComEstoque("10");
        movimentacao(p, TipoMovimentacao.ENTRADA, "5").aplicarNoProduto();
        assertEquals(0, new BigDecimal("15").compareTo(p.getEstoqueAtual()));

        movimentacao(p, TipoMovimentacao.SAIDA, "4").aplicarNoProduto();
        assertEquals(0, new BigDecimal("11").compareTo(p.getEstoqueAtual()));

        movimentacao(p, TipoMovimentacao.AJUSTE, "7").aplicarNoProduto();
        assertEquals(0, new BigDecimal("7").compareTo(p.getEstoqueAtual()));
    }

    @Test
    void saidaMaiorQueOEstoqueGeraErro() {
        Produto p = produtoComEstoque("1");
        assertThrows(IllegalStateException.class,
                () -> movimentacao(p, TipoMovimentacao.SAIDA, "2").aplicarNoProduto());
    }

    @Test
    void quantidadeZeroOuSemTipoGeraErro() {
        Produto p = produtoComEstoque("1");
        assertThrows(IllegalArgumentException.class,
                () -> movimentacao(p, TipoMovimentacao.AJUSTE, "0").aplicarNoProduto());
        assertThrows(IllegalStateException.class,
                () -> movimentacao(p, null, "1").aplicarNoProduto());
    }
}
