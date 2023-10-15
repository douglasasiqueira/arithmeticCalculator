ALTER TABLE IF EXISTS record
    ADD deleted boolean not null default(false)