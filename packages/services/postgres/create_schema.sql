BEGIN;
    create table farms
    (
        id serial not null
            constraint id_pkey
            primary key,
        user_id varchar(100) null,
        name varchar(250) not null,
        geom text null,
        altitude integer null,
        city varchar(200) null,
        state varchar(200) null,
        country varchar(200) null,
        country_abbreviation varchar(2) null,
        created_at timestamp with time zone default now() not null,
        updated_at timestamp with time zone default now() not null
    );

    insert into farms(user_id, name) values ('345ae4d0-f2c3-4342-91a2-5b45cb8db57f','Farm 01');

    insert into farms(user_id, name, city, state, country, country_abbreviation)
    values ('16c1ef84-df72-4be1-ad46-1168ee53cd60','Farm 02', 'San Francisco', 'California', 'United States of America', 'US');

    insert into farms(name, geom, altitude, city, state, country, country_abbreviation)
    values ('Farm 03', '0101000020E61000000100008868A55EC0724CA4518EDE4240', 760, 'Sao Paulo', 'Sao Paulo', 'Brasil', 'BR');

    insert into farms(name, geom, city, state, country, country_abbreviation)
    values ('Farm 04', '0101000020E61000000100008868A55EC0724CA4518EDE4240', 'Sao Paulo', 'Sao Paulo', 'Brazil', 'BR');
COMMIT;
