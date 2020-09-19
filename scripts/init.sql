BEGIN;
    create table ordem_ativo
    (
        id serial not null
            constraint id_pkey
            primary key,
        ativo varchar(10) not null,
        token varchar(100) not null,
        codigo_precificacao varchar(2) not null,
        quantidade INTEGER not null,
        preco DOUBLE PRECISION not null,
        total DOUBLE PRECISION not null,
        created_at timestamp with time zone default now() not null
    );
COMMIT;
