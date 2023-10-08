CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    balance decimal(10,2) NOT NULL default(0),
    status varchar(255),
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL
);

CREATE UNIQUE INDEX ON users(username);