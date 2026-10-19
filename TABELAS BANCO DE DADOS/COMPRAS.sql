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