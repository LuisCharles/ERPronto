-- Table: public.compras_itens

-- DROP TABLE IF EXISTS public.compras_itens;

CREATE TABLE IF NOT EXISTS public.compras_itens
(
    id serial NOT NULL,
    compra_id integer NOT NULL,
    produto_id integer NOT NULL,
    quantidade numeric NOT NULL,
    preco_unitario numeric NOT NULL,
    subtotal numeric NOT NULL,
    CONSTRAINT compras_itens_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.compras_itens
    OWNER to postgres;