-- Table: public.perfil_permissao

-- DROP TABLE IF EXISTS public.perfil_permissao;

CREATE TABLE IF NOT EXISTS public.perfil_permissao
(
    perfil_id integer NOT NULL,
    permissao_id integer NOT NULL,
    CONSTRAINT perfil_permissao_pkey PRIMARY KEY (perfil_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.perfil_permissao
    OWNER to postgres;