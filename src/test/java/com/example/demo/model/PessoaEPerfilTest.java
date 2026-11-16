package com.example.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaEPerfilTest {

    @Test
    void enderecoCompletoJuntaAsPartesPreenchidas() {
        Cliente c = new Cliente();
        c.setLogradouro("Rua A");
        c.setNumero("10");
        c.setBairro("Centro");
        c.setCidade("São José do Rio Preto");
        c.setEstado("SP");
        c.setCep("15000-000");
        assertEquals("Rua A, 10 - Centro - São José do Rio Preto/SP - CEP 15000-000", c.getEnderecoCompleto());
    }

    @Test
    void enderecoCompletoPulaPartesVaziasESemEnderecoFicaVazio() {
        Cliente c = new Cliente();
        c.setLogradouro("Rua A");
        c.setCidade("Mirassol");
        assertEquals("Rua A - Mirassol", c.getEnderecoCompleto());
        assertEquals("", new Cliente().getEnderecoCompleto());
    }

    @Test
    void usuarioTemAsPermissoesDoSeuPerfil() {
        Permissao criarVenda = new Permissao();
        criarVenda.setNome("VENDAS_CRIAR");
        Perfil vendedor = new Perfil();
        vendedor.adicionarPermissao(criarVenda);

        Usuario u = new Usuario();
        u.setPerfil(vendedor);

        assertTrue(u.possuiPermissao("vendas_criar"));
        assertFalse(u.possuiPermissao("ESTOQUE_AJUSTAR"));
        assertFalse(u.possuiPermissao(null));

        vendedor.removerPermissao(criarVenda);
        assertFalse(u.possuiPermissao("VENDAS_CRIAR"));
    }

    @Test
    void usuarioSemPerfilNaoTemPermissao() {
        assertFalse(new Usuario().possuiPermissao("VENDAS_CRIAR"));
    }

    @Test
    void ativoVazioViraAtivo() {
        Fornecedor f = new Fornecedor();
        f.setAtivo(null);
        assertTrue(f.getAtivo());
        f.desativar();
        assertFalse(f.getAtivo());
    }
}
