CREATE TABLE city
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

alter table candidates
    add city_id int;

alter table candidates
    add constraint candidates_city_id_fk
        foreign key (city_id) references city;
