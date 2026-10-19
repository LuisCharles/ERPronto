CREATE TABLE dbo.permissoes (
 id INT IDENTITY(1,1) NOT NULL,
 nome VARCHAR(80) NOT NULL,
 descricao VARCHAR(150) NULL,
 CONSTRAINT PK_permissoes PRIMARY KEY (id),
 CONSTRAINT UQ_permissoes_nome UNIQUE (nome)
);
GO