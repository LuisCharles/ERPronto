CREATE TABLE dbo.usuarios (
 id INT IDENTITY(1,1) NOT NULL,
 nome VARCHAR(100) NOT NULL,
 email VARCHAR(150) NOT NULL,
 senha_hash VARCHAR(255) NOT NULL,
 perfil_id INT NOT NULL,
 ativo BIT NOT NULL CONSTRAINT DF_usuarios_ativo DEFAULT (1),
 atualizado_em DATETIME2 NULL,
 criado_em DATETIME2 NOT NULL CONSTRAINT DF_usuarios_criado_em DEFAULT (SYSDATETIME()),
 CONSTRAINT PK_usuarios PRIMARY KEY (id),
 CONSTRAINT UQ_usuarios_email UNIQUE (email),
 CONSTRAINT FK_usuarios_perfis FOREIGN KEY (perfil_id) REFERENCES dbo.perfis(id)
);
GO