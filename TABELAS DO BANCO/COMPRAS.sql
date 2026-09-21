-- Table: public.compras

-- DROP TABLE IF EXISTS public.compras;

CREATE TABLE IF NOT EXISTS public.compras
(
    id serial NOT NULL,
    fornecedor_id integer NOT NULL,
    usuario_id integer NOT NULL,
    data_compra timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status character varying COLLATE pg_catalog."default",
    subtotal numeric NOT NULL DEFAULT 0,
    desconto numeric NOT NULL DEFAULT 0,
    total numeric NOT NULL DEFAULT 0,
    criado_em timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em timestamp with time zone,
    CONSTRAINT compras_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.compras
    OWNER to postgres;