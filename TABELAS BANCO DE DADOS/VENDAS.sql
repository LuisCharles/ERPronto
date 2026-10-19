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