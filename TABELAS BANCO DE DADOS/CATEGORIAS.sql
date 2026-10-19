CREATE TABLE dbo.categorias (
 id INT IDENTITY(1,1) NOT NULL,
 nome VARCHAR(80) NOT NULL,
 descricao VARCHAR(150) NULL,
 ativo BIT NOT NULL CONSTRAINT DF_categorias_ativo DEFAULT (1),
 CONSTRAINT PK_categorias PRIMARY KEY (id)
);
GO

