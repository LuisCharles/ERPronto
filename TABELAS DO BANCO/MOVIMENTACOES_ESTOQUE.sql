-- Table: public.movimentacoes_estoque

-- DROP TABLE IF EXISTS public.movimentacoes_estoque;

CREATE TABLE IF NOT EXISTS public.movimentacoes_estoque
(
    id serial NOT NULL,
    produto_id integer NOT NULL,
    usuario_id integer NOT NULL,
    tipo character varying COLLATE pg_catalog."default" NOT NULL,
    quantidade numeric NOT NULL,
    observacao character varying COLLATE pg_catalog."default",
    criado_em timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT movimentacoes_estoque_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.movimentacoes_estoque
    OWNER to postgres;