-- Table: public.fornecedores

-- DROP TABLE IF EXISTS public.fornecedores;

CREATE TABLE IF NOT EXISTS public.fornecedores
(
    id serial NOT NULL,
    razao_social character varying COLLATE pg_catalog."default" NOT NULL,
    nome_fantasia character varying COLLATE pg_catalog."default",
    cnpj character varying COLLATE pg_catalog."default",
    email character varying COLLATE pg_catalog."default",
    telefone character varying COLLATE pg_catalog."default",
    logradouro character varying COLLATE pg_catalog."default",
    numero character varying COLLATE pg_catalog."default",
    bairro character varying COLLATE pg_catalog."default",
    cidade character varying COLLATE pg_catalog."default",
    estado "char",
    cep character varying COLLATE pg_catalog."default",
    ativo boolean NOT NULL DEFAULT true,
    criado_em timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em timestamp with time zone,
    CONSTRAINT fornecedores_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.fornecedores
    OWNER to postgres;