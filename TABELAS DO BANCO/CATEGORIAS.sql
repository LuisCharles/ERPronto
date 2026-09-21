-- Table: public.categorias

-- DROP TABLE IF EXISTS public.categorias;

CREATE TABLE IF NOT EXISTS public.categorias
(
    nome character varying COLLATE pg_catalog."default" NOT NULL,
    descricao character varying COLLATE pg_catalog."default",
    ativo boolean NOT NULL DEFAULT true,
    "ID" serial NOT NULL,
    CONSTRAINT categorias_pkey PRIMARY KEY ("ID")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.categorias
    OWNER to postgres;