CREATE TABLE dbo.perfil_permissao (
 perfil_id INT NOT NULL,
 permissao_id INT NOT NULL,
 CONSTRAINT PK_perfil_permissao PRIMARY KEY (perfil_id, permissao_id),
 CONSTRAINT FK_perfil_permissao_perfis FOREIGN KEY (perfil_id) REFERENCES dbo.perfis(id),
 CONSTRAINT FK_perfil_permissao_permissoes FOREIGN KEY (permissao_id) REFERENCES dbo.permissoes(id)
);
GO