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
 CONSTRAINT CK_contas_financeiras_status CHECK (status IN ('PENDENTE','PAGO','VENCIDO','CANCELADO'))
);
GO