package com.example.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Sobe a aplicação inteira (com o banco H2 em memória) e chama a API de verdade, com login. */
@SpringBootTest
@AutoConfigureMockMvc
class ApiComNovoModeloTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper json;

    private String token;

    @BeforeEach
    void fazerLoginComoAdmin() throws Exception {
        String resposta = mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"admin@erp.com\",\"password\":\"admin123\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        token = "Bearer " + json.readTree(resposta).get("token").asText();
    }

    @Test
    void listaDeUsuariosContinuaComPerfilIdESemSenha() throws Exception {
        mvc.perform(get("/api/usuarios").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("admin@erp.com"))
                .andExpect(jsonPath("$[0].perfilId").isNumber())
                .andExpect(jsonPath("$[0].senhaHash").doesNotExist())
                .andExpect(jsonPath("$[0].perfil").doesNotExist());
    }

    @Test
    void cadastrarUsuarioComPerfilInexistenteRetorna400() throws Exception {
        mvc.perform(post("/api/usuarios").header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Ana\",\"email\":\"ana@erp.com\",\"senha\":\"123456\",\"perfilId\":999}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("999")));
    }

    @Test
    void fornecedorSemNomeUsaARazaoSocialEGuardaOCnpjEmCpfCnpj() throws Exception {
        mvc.perform(post("/api/fornecedores").header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"razaoSocial\":\"Distribuidora Rio Preto LTDA\",\"cpfCnpj\":\"12.345.678/0001-90\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Distribuidora Rio Preto LTDA"))
                .andExpect(jsonPath("$.cpfCnpj").value("12.345.678/0001-90"))
                .andExpect(jsonPath("$.ativo").value(true));
    }

    @Test
    void semLoginAApiRecusa() throws Exception {
        mvc.perform(get("/api/clientes")).andExpect(status().is4xxClientError());
    }
}
