-- Table: public.permissoes

-- DROP TABLE IF EXISTS public.permissoes;

CREATE TABLE IF NOT EXISTS public.permissoes
(
    id serial NOT NULL,
    nome character varying COLLATE pg_catalog."default" NOT NULL,
    descricao character varying COLLATE pg_catalog."default",
    CONSTRAINT permissoes_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.permissoes
    OWNER to postgres;