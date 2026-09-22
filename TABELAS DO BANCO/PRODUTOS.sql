-- Table: public.Produtos

-- DROP TABLE IF EXISTS public."Produtos";

CREATE TABLE IF NOT EXISTS public."Produtos"
(
    id serial NOT NULL,
    categoria_id integer,
    nome character varying COLLATE pg_catalog."default" NOT NULL,
    codigo character varying COLLATE pg_catalog."default",
    descricao character varying COLLATE pg_catalog."default",
    preco_custo numeric NOT NULL DEFAULT 0,
    preco_venda numeric NOT NULL DEFAULT 0,
    estoque_atual numeric NOT NULL DEFAULT 0,
    estoque_minimo numeric NOT NULL DEFAULT 0,
    ativo boolean NOT NULL,
    criado_em timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em timestamp with time zone,
    CONSTRAINT "Produtos_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Produtos"
    OWNER to postgres;