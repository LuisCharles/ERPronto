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