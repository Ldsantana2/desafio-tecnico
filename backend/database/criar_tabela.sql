CREATE TABLE users_table (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(255),
    telefone VARCHAR(255),
    website VARCHAR(255),
    street VARCHAR(255),
    suite VARCHAR(255),
    city VARCHAR(255),
    zipcode VARCHAR(255),
    company_name VARCHAR(255),
    catch_phrase VARCHAR(255),
    bs VARCHAR(255)
);