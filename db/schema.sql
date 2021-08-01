CREATE TABLE post
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT
);

CREATE TABLE candidates
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT,
    password varchar(100)
);