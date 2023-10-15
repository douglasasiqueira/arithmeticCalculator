ALTER TABLE IF EXISTS users
    ADD version bigint not null default(1)