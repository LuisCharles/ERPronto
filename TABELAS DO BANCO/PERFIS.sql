-- Table: public.Perfis

-- DROP TABLE IF EXISTS public."Perfis";

CREATE TABLE IF NOT EXISTS public."Perfis"
(
    id serial NOT NULL,
    nome character varying COLLATE pg_catalog."default" NOT NULL,
    descricao character varying COLLATE pg_catalog."default",
    CONSTRAINT "Perfis_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Perfis"
    OWNER to postgres;