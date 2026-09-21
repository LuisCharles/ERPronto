-- Table: public.vendas_itens

-- DROP TABLE IF EXISTS public.vendas_itens;

CREATE TABLE IF NOT EXISTS public.vendas_itens
(
    id serial NOT NULL,
    venda_id integer NOT NULL,
    produto_id integer NOT NULL,
    quantidade numeric NOT NULL,
    preco_unitario numeric NOT NULL,
    desconto numeric NOT NULL DEFAULT 0,
    subtotal numeric NOT NULL,
    CONSTRAINT vendas_itens_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.vendas_itens
    OWNER to postgres;