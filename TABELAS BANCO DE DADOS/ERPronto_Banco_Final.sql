/*
 ERPronto - Microsoft SQL Server
 Execute em um banco de dados de destino vazio, selecionado no SSMS.
 Este script cria a estrutura; nao cria/apaga o banco nem migra registros.
 Execute uma unica vez. Nao rode em banco com tabelas ja criadas.
 Versao: 15 tabelas, Pessoa ISA Cliente/Fornecedor/Usuario.
*/
SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
GO

CREATE TABLE dbo.categorias (
 id INT IDENTITY(1,1) NOT NULL,
 nome VARCHAR(80) NOT NULL,
 descricao VARCHAR(150) NULL,
 ativo BIT NOT NULL CONSTRAINT DF_categorias_ativo DEFAULT (1),
 CONSTRAINT PK_categorias PRIMARY KEY (id)
);
GO

-- Superclasse PESSOA: dados comuns das especializacoes.
CREATE TABLE dbo.pessoas (
 id INT IDENTITY(1,1) NOT NULL,
 nome VARCHAR(120) NOT NULL,
 cpf_cnpj VARCHAR(18) NULL,
 email VARCHAR(150) NULL,
 telefone VARCHAR(20) NULL,
 logradouro VARCHAR(120) NULL,
 numero VARCHAR(20) NULL,
 bairro VARCHAR(80) NULL,
 cidade VARCHAR(80) NULL,
 estado CHAR(2) NULL,
 cep VARCHAR(9) NULL,
 criado_em DATETIME2 NOT NULL CONSTRAINT DF_pessoas_criado_em DEFAULT (SYSDATETIME()),
 atualizado_em DATETIME2 NULL,
 CONSTRAINT PK_pessoas PRIMARY KEY (id),
 CONSTRAINT CK_pessoas_estado CHECK (estado IS NULL OR estado COLLATE Latin1_General_100_BIN2 IN
 ('AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'))
);
GO
CREATE UNIQUE INDEX UX_pessoas_cpf_cnpj ON dbo.pessoas(cpf_cnpj) WHERE cpf_cnpj IS NOT NULL;
CREATE UNIQUE INDEX UX_pessoas_email ON dbo.pessoas(email) WHERE email IS NOT NULL;
GO

CREATE TABLE dbo.clientes (
 id INT IDENTITY(1,1) NOT NULL,
 pessoa_id INT NOT NULL,
 ativo BIT NOT NULL CONSTRAINT DF_clientes_ativo DEFAULT (1),
 criado_em DATETIME2 NOT NULL CONSTRAINT DF_clientes_criado_em DEFAULT (SYSDATETIME()),
 atualizado_em DATETIME2 NULL,
 CONSTRAINT PK_clientes PRIMARY KEY (id),
 CONSTRAINT FK_clientes_pessoas FOREIGN KEY (pessoa_id) REFERENCES dbo.pessoas(id)
);
GO
CREATE UNIQUE INDEX UX_clientes_pessoa_id ON dbo.clientes(pessoa_id);
GO

CREATE TABLE dbo.fornecedores (
 id INT IDENTITY(1,1) NOT NULL,
 pessoa_id INT NOT NULL,
 razao_social VARCHAR(120) NOT NULL,
 nome_fantasia VARCHAR(120) NULL,
 ativo BIT NOT NULL CONSTRAINT DF_fornecedores_ativo DEFAULT (1),
 criado_em DATETIME2 NOT NULL CONSTRAINT DF_fornecedores_criado_em DEFAULT (SYSDATETIME()),
 atualizado_em DATETIME2 NULL,
 CONSTRAINT PK_fornecedores PRIMARY KEY (id),
 CONSTRAINT FK_fornecedores_pessoas FOREIGN KEY (pessoa_id) REFERENCES dbo.pessoas(id)
);
GO
CREATE UNIQUE INDEX UX_fornecedores_pessoa_id ON dbo.fornecedores(pessoa_id);
GO

CREATE TABLE dbo.perfis (
 id INT IDENTITY(1,1) NOT NULL,
 nome VARCHAR(50) NOT NULL,
 descricao VARCHAR(150) NULL,
 CONSTRAINT PK_perfis PRIMARY KEY (id),
 CONSTRAINT UQ_perfis_nome UNIQUE (nome)
);
GO

CREATE TABLE dbo.permissoes (
 id INT IDENTITY(1,1) NOT NULL,
 nome VARCHAR(80) NOT NULL,
 descricao VARCHAR(150) NULL,
 CONSTRAINT PK_permissoes PRIMARY KEY (id),
 CONSTRAINT UQ_permissoes_nome UNIQUE (nome)
);
GO

CREATE TABLE dbo.perfil_permissao (
 perfil_id INT NOT NULL,
 permissao_id INT NOT NULL,
 CONSTRAINT PK_perfil_permissao PRIMARY KEY (perfil_id, permissao_id),
 CONSTRAINT FK_perfil_permissao_perfis FOREIGN KEY (perfil_id) REFERENCES dbo.perfis(id),
 CONSTRAINT FK_perfil_permissao_permissoes FOREIGN KEY (permissao_id) REFERENCES dbo.permissoes(id)
);
GO

CREATE TABLE dbo.usuarios (
 id INT IDENTITY(1,1) NOT NULL,
 pessoa_id INT NOT NULL,
 senha_hash VARCHAR(255) NOT NULL,
 perfil_id INT NOT NULL,
 ativo BIT NOT NULL CONSTRAINT DF_usuarios_ativo DEFAULT (1),
 atualizado_em DATETIME2 NULL,
 criado_em DATETIME2 NOT NULL CONSTRAINT DF_usuarios_criado_em DEFAULT (SYSDATETIME()),
 CONSTRAINT PK_usuarios PRIMARY KEY (id),
 CONSTRAINT FK_usuarios_pessoas FOREIGN KEY (pessoa_id) REFERENCES dbo.pessoas(id),
 CONSTRAINT FK_usuarios_perfis FOREIGN KEY (perfil_id) REFERENCES dbo.perfis(id)
);
GO
CREATE UNIQUE INDEX UX_usuarios_pessoa_id ON dbo.usuarios(pessoa_id);
GO

CREATE TABLE dbo.produtos (
 id INT IDENTITY(1,1) NOT NULL,
 categoria_id INT NULL,
 nome VARCHAR(100) NOT NULL,
 codigo VARCHAR(50) NULL,
 descricao VARCHAR(200) NULL,
 preco_custo DECIMAL(10,2) NOT NULL CONSTRAINT DF_produtos_preco_custo DEFAULT (0),
 preco_venda DECIMAL(10,2) NOT NULL CONSTRAINT DF_produtos_preco_venda DEFAULT (0),
 estoque_atual DECIMAL(10,2) NOT NULL CONSTRAINT DF_produtos_estoque_atual DEFAULT (0),
 estoque_minimo DECIMAL(10,2) NOT NULL CONSTRAINT DF_produtos_estoque_minimo DEFAULT (0),
 ativo BIT NOT NULL CONSTRAINT DF_produtos_ativo DEFAULT (1),
 criado_em DATETIME2 NOT NULL CONSTRAINT DF_produtos_criado_em DEFAULT (SYSDATETIME()),
 atualizado_em DATETIME2 NULL,
 CONSTRAINT PK_produtos PRIMARY KEY (id),
 CONSTRAINT FK_produtos_categorias FOREIGN KEY (categoria_id) REFERENCES dbo.categorias(id),
 CONSTRAINT CK_produtos_preco_custo CHECK (preco_custo >= 0),
 CONSTRAINT CK_produtos_preco_venda CHECK (preco_venda >= 0),
 CONSTRAINT CK_produtos_estoque_atual CHECK (estoque_atual >= 0),
 CONSTRAINT CK_produtos_estoque_minimo CHECK (estoque_minimo >= 0)
);
GO
CREATE UNIQUE INDEX UX_produtos_codigo ON dbo.produtos(codigo) WHERE codigo IS NOT NULL;
GO

CREATE TABLE dbo.vendas (
 id INT IDENTITY(1,1) NOT NULL,
 cliente_id INT NOT NULL,
 usuario_id INT NOT NULL,
 data_venda DATETIME2 NOT NULL CONSTRAINT DF_vendas_data_venda DEFAULT (SYSDATETIME()),
 status VARCHAR(20) NOT NULL CONSTRAINT DF_vendas_status DEFAULT ('PENDENTE'),
 subtotal DECIMAL(10,2) NOT NULL CONSTRAINT DF_vendas_subtotal DEFAULT (0),
 desconto DECIMAL(10,2) NOT NULL CONSTRAINT DF_vendas_desconto DEFAULT (0),
 total DECIMAL(10,2) NOT NULL CONSTRAINT DF_vendas_total DEFAULT (0),
 criado_em DATETIME2 NOT NULL CONSTRAINT DF_vendas_criado_em DEFAULT (SYSDATETIME()),
 atualizado_em DATETIME2 NULL,
 CONSTRAINT PK_vendas PRIMARY KEY (id),
 CONSTRAINT FK_vendas_clientes FOREIGN KEY (cliente_id) REFERENCES dbo.clientes(id),
 CONSTRAINT FK_vendas_usuarios FOREIGN KEY (usuario_id) REFERENCES dbo.usuarios(id),
 CONSTRAINT CK_vendas_status CHECK (status IN ('PENDENTE','FINALIZADA','CANCELADA')),
 CONSTRAINT CK_vendas_subtotal CHECK (subtotal >= 0),
 CONSTRAINT CK_vendas_desconto CHECK (desconto >= 0 AND desconto <= subtotal),
 CONSTRAINT CK_vendas_total CHECK (total >= 0)
);
GO

CREATE TABLE dbo.vendas_itens (
 id INT IDENTITY(1,1) NOT NULL,
 venda_id INT NOT NULL,
 produto_id INT NOT NULL,
 quantidade DECIMAL(10,2) NOT NULL,
 preco_unitario DECIMAL(10,2) NOT NULL,
 desconto DECIMAL(10,2) NOT NULL CONSTRAINT DF_vendas_itens_desconto DEFAULT (0),
 subtotal DECIMAL(10,2) NOT NULL,
 CONSTRAINT PK_vendas_itens PRIMARY KEY (id),
 CONSTRAINT FK_vendas_itens_vendas FOREIGN KEY (venda_id) REFERENCES dbo.vendas(id),
 CONSTRAINT FK_vendas_itens_produtos FOREIGN KEY (produto_id) REFERENCES dbo.produtos(id),
 CONSTRAINT CK_vendas_itens_quantidade CHECK (quantidade > 0),
 CONSTRAINT CK_vendas_itens_preco_unitario CHECK (preco_unitario >= 0),
 CONSTRAINT CK_vendas_itens_desconto CHECK (desconto >= 0),
 CONSTRAINT CK_vendas_itens_subtotal CHECK (subtotal >= 0)
);
GO

CREATE TABLE dbo.compras (
 id INT IDENTITY(1,1) NOT NULL,
 fornecedor_id INT NOT NULL,
 usuario_id INT NOT NULL,
 data_compra DATETIME2 NOT NULL CONSTRAINT DF_compras_data_compra DEFAULT (SYSDATETIME()),
 status VARCHAR(20) NOT NULL CONSTRAINT DF_compras_status DEFAULT ('PENDENTE'),
 subtotal DECIMAL(10,2) NOT NULL CONSTRAINT DF_compras_subtotal DEFAULT (0),
 desconto DECIMAL(10,2) NOT NULL CONSTRAINT DF_compras_desconto DEFAULT (0),
 total DECIMAL(10,2) NOT NULL CONSTRAINT DF_compras_total DEFAULT (0),
 criado_em DATETIME2 NOT NULL CONSTRAINT DF_compras_criado_em DEFAULT (SYSDATETIME()),
 atualizado_em DATETIME2 NULL,
 CONSTRAINT PK_compras PRIMARY KEY (id),
 CONSTRAINT FK_compras_fornecedores FOREIGN KEY (fornecedor_id) REFERENCES dbo.fornecedores(id),
 CONSTRAINT FK_compras_usuarios FOREIGN KEY (usuario_id) REFERENCES dbo.usuarios(id),
 CONSTRAINT CK_compras_status CHECK (status IN ('PENDENTE','FINALIZADA','CANCELADA')),
 CONSTRAINT CK_compras_subtotal CHECK (subtotal >= 0),
 CONSTRAINT CK_compras_desconto CHECK (desconto >= 0 AND desconto <= subtotal),
 CONSTRAINT CK_compras_total CHECK (total >= 0)
);
GO

CREATE TABLE dbo.compras_itens (
 id INT IDENTITY(1,1) NOT NULL,
 compra_id INT NOT NULL,
 produto_id INT NOT NULL,
 quantidade DECIMAL(10,2) NOT NULL,
 preco_unitario DECIMAL(10,2) NOT NULL,
 subtotal DECIMAL(10,2) NOT NULL,
 CONSTRAINT PK_compras_itens PRIMARY KEY (id),
 CONSTRAINT FK_compras_itens_compras FOREIGN KEY (compra_id) REFERENCES dbo.compras(id),
 CONSTRAINT FK_compras_itens_produtos FOREIGN KEY (produto_id) REFERENCES dbo.produtos(id),
 CONSTRAINT CK_compras_itens_quantidade CHECK (quantidade > 0),
 CONSTRAINT CK_compras_itens_preco_unitario CHECK (preco_unitario >= 0),
 CONSTRAINT CK_compras_itens_subtotal CHECK (subtotal >= 0)
);
GO

CREATE TABLE dbo.movimentacoes_estoque (
 id INT IDENTITY(1,1) NOT NULL,
 produto_id INT NOT NULL,
 usuario_id INT NOT NULL,
 tipo VARCHAR(20) NOT NULL,
 quantidade DECIMAL(10,2) NOT NULL,
 observacao VARCHAR(200) NULL,
 criado_em DATETIME2 NOT NULL CONSTRAINT DF_movimentacoes_estoque_criado_em DEFAULT (SYSDATETIME()),
 CONSTRAINT PK_movimentacoes_estoque PRIMARY KEY (id),
 CONSTRAINT FK_movimentacoes_estoque_produtos FOREIGN KEY (produto_id) REFERENCES dbo.produtos(id),
 CONSTRAINT FK_movimentacoes_estoque_usuarios FOREIGN KEY (usuario_id) REFERENCES dbo.usuarios(id),
 CONSTRAINT CK_movimentacoes_estoque_tipo CHECK (tipo IN ('ENTRADA','SAIDA','AJUSTE')),
 CONSTRAINT CK_movimentacoes_estoque_quantidade CHECK (quantidade > 0)
);
GO

CREATE TABLE dbo.contas_financeiras (
 id INT IDENTITY(1,1) NOT NULL,
 tipo VARCHAR(10) NOT NULL,
 descricao VARCHAR(150) NULL,
 valor DECIMAL(10,2) NOT NULL,
 data_vencimento DATE NOT NULL,
 data_pagamento DATE NULL,
 status VARCHAR(20) NOT NULL CONSTRAINT DF_contas_financeiras_status DEFAULT ('PENDENTE'),
 venda_id INT NULL,
 compra_id INT NULL,
 criado_em DATETIME2 NOT NULL CONSTRAINT DF_contas_financeiras_criado_em DEFAULT (SYSDATETIME()),
 atualizado_em DATETIME2 NULL,
 CONSTRAINT PK_contas_financeiras PRIMARY KEY (id),
 CONSTRAINT FK_contas_financeiras_vendas FOREIGN KEY (venda_id) REFERENCES dbo.vendas(id),
 CONSTRAINT FK_contas_financeiras_compras FOREIGN KEY (compra_id) REFERENCES dbo.compras(id),
 CONSTRAINT CK_contas_financeiras_tipo CHECK (tipo IN ('PAGAR','RECEBER')),
 CONSTRAINT CK_contas_financeiras_valor CHECK (valor > 0),
 CONSTRAINT CK_contas_financeiras_status CHECK (status IN ('PENDENTE','PAGO','VENCIDO','CANCELADO')),
 CONSTRAINT CK_contas_financeiras_origem CHECK (venda_id IS NULL OR compra_id IS NULL)
);
GO

/* Fim do script: 15 tabelas, 19 FKs. */
