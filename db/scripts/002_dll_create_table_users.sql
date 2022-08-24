CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    email    TEXT,
    password TEXT,
    CONSTRAINT email_unique UNIQUE (email)
);
