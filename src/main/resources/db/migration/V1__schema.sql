DROP TABLE IF EXISTS example_entity;

CREATE TABLE example_entity(
    id   BIGSERIAL NOT NULL UNIQUE PRIMARY KEY,
    name  VARCHAR(255) NOT NULL
);