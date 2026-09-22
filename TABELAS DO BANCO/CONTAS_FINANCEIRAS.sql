-- Table: public.contas_financeiras

-- DROP TABLE IF EXISTS public.contas_financeiras;

CREATE TABLE IF NOT EXISTS public.contas_financeiras
(
    id serial NOT NULL,
    tipo character varying COLLATE pg_catalog."default" NOT NULL,
    descricao character varying COLLATE pg_catalog."default" NOT NULL,
    valor numeric NOT NULL,
    data_vencimento date NOT NULL,
    data_pagamento date,
    status character varying COLLATE pg_catalog."default" NOT NULL,
    venda_id integer,
    compra_id integer,
    criado_em timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em timestamp with time zone,
    CONSTRAINT contas_financeiras_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.contas_financeiras
    OWNER to postgres;