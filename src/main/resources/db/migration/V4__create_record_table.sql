CREATE TABLE IF NOT EXISTS record (
    id SERIAL PRIMARY KEY,
    operation_id integer REFERENCES operation(id) NOT NULL,
    user_id integer REFERENCES users(id) NOT NULL,
    amount decimal(10,2) NOT NULL,
    user_balance decimal(10,2) NOT NULL,
    operation_response TEXT NOT NULL,
    date timestamp NOT NULL
);