create table post (
    id serial primary key,
    name text,
    description text,
    created timestamp,
    visible bool,
    city_id integer
);