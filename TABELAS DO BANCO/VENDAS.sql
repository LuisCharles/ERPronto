-- Table: public.vendas

-- DROP TABLE IF EXISTS public.vendas;

CREATE TABLE IF NOT EXISTS public.vendas
(
    id serial NOT NULL,
    cliente_id integer NOT NULL,
    usuario_id integer NOT NULL,
    data_venda timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status character varying COLLATE pg_catalog."default" NOT NULL,
    subtotal numeric NOT NULL DEFAULT 0,
    desconto numeric NOT NULL DEFAULT 0,
    total numeric NOT NULL DEFAULT 0,
    criado_em timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em timestamp with time zone,
    CONSTRAINT vendas_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.vendas
    OWNER to postgres;