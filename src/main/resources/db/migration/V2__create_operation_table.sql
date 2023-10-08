CREATE TABLE IF NOT EXISTS operation (
    id SERIAL PRIMARY KEY,
    type varchar(255) NOT NULL,
    cost decimal(10,2) NOT NULL
);

CREATE UNIQUE INDEX ON operation(type);