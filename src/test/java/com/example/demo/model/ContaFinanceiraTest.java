package com.example.demo.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContaFinanceiraTest {

    private ContaFinanceira contaVencendoEm(LocalDate vencimento) {
        ContaFinanceira c = new ContaFinanceira();
        c.setTipo(TipoConta.RECEBER);
        c.setValor(new BigDecimal("100.00"));
        c.setDataVencimento(vencimento);
        return c;
    }

    @Test
    void registrarPagamentoGuardaADataEMarcaComoPago() {
        LocalDate hoje = LocalDate.now();
        ContaFinanceira c = contaVencendoEm(hoje);
        c.registrarPagamento(hoje);
        assertEquals(StatusConta.PAGO, c.getStatus());
        assertEquals(hoje, c.getDataPagamento());
    }

    @Test
    void naoPagaDuasVezesNemContaCanceladaNemSemData() {
        ContaFinanceira paga = contaVencendoEm(LocalDate.now());
        paga.registrarPagamento(LocalDate.now());
        assertThrows(IllegalStateException.class, () -> paga.registrarPagamento(LocalDate.now()));

        ContaFinanceira cancelada = contaVencendoEm(LocalDate.now());
        cancelada.cancelar();
        assertThrows(IllegalStateException.class, () -> cancelada.registrarPagamento(LocalDate.now()));

        assertThrows(IllegalArgumentException.class, () -> contaVencendoEm(LocalDate.now()).registrarPagamento(null));
    }

    @Test
    void contaPagaNaoPodeSerCancelada() {
        ContaFinanceira c = contaVencendoEm(LocalDate.now());
        c.registrarPagamento(LocalDate.now());
        assertThrows(IllegalStateException.class, c::cancelar);
    }

    @Test
    void venceSoDepoisDoDiaDoVencimento() {
        assertTrue(contaVencendoEm(LocalDate.now().minusDays(1)).isVencida());
        assertFalse(contaVencendoEm(LocalDate.now()).isVencida());

        ContaFinanceira paga = contaVencendoEm(LocalDate.now().minusDays(10));
        paga.registrarPagamento(LocalDate.now());
        assertFalse(paga.isVencida());
    }
}
