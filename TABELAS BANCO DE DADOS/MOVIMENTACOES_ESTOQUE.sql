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