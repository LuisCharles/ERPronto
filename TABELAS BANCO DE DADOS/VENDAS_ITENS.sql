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
 --CONSTRAINT CK_vendas_itens_subtotal CHECK (subtotal >= 0)
);
GO