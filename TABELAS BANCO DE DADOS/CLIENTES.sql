CREATE TABLE dbo.clientes (
 id INT IDENTITY(1,1) NOT NULL,
 nome VARCHAR(100) NOT NULL,
 cpf_cnpj VARCHAR(18) NULL,
 email VARCHAR(150) NULL,
 telefone VARCHAR(20) NULL,
 logradouro VARCHAR(120) NULL,
 numero VARCHAR(20) NULL,
 bairro VARCHAR(80) NULL,
 cidade VARCHAR(80) NULL,
 estado CHAR(2) NULL,
 cep VARCHAR(9) NULL,
 ativo BIT NOT NULL CONSTRAINT DF_clientes_ativo DEFAULT (1),
 criado_em DATETIME2 NOT NULL CONSTRAINT DF_clientes_criado_em DEFAULT (SYSDATETIME()),
 atualizado_em DATETIME2 NULL,
 CONSTRAINT PK_clientes PRIMARY KEY (id),
 CONSTRAINT CK_clientes_estado CHECK (estado IS NULL OR estado COLLATE Latin1_General_100_BIN2 IN
 ('AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'))
);
GO
CREATE UNIQUE INDEX UX_clientes_cpf_cnpj ON dbo.clientes(cpf_cnpj) WHERE cpf_cnpj IS NOT NULL;
CREATE UNIQUE INDEX UX_clientes_email ON dbo.clientes(email) WHERE email IS NOT NULL;
GO