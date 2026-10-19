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