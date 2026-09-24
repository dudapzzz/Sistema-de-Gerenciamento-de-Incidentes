CREATE TABLE incidente (
                           codigo SERIAL PRIMARY KEY,
                           titulo VARCHAR(150) NOT NULL,
                           descricao TEXT,
                           relevancia VARCHAR(20) NOT NULL,
                           data_incidente DATE DEFAULT CURRENT_DATE,
                           status VARCHAR(20) NOT NULL,
                           responsavel VARCHAR(50),
                           usuario_id INTEGER NOT NULL,
                           CONSTRAINT fk_usuario_incidente
                               FOREIGN KEY (usuario_id) REFERENCES usuario(codigo) ON DELETE CASCADE
);