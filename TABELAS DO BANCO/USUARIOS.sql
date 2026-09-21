-- Table: public.Usuarios

-- DROP TABLE IF EXISTS public."Usuarios";

CREATE TABLE IF NOT EXISTS public."Usuarios"
(
    id serial NOT NULL,
    nome character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    senha_hash character varying COLLATE pg_catalog."default" NOT NULL,
    perfil_id integer NOT NULL,
    ativo boolean NOT NULL DEFAULT true,
    atualizado_em timestamp with time zone,
    criado_em timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "Usuarios_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Usuarios"
    OWNER to postgres;