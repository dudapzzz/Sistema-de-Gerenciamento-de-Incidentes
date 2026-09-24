CREATE TABLE usuario (
                         codigo SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         senha VARCHAR(100) NOT NULL,
                         ativo BOOLEAN DEFAULT TRUE
);