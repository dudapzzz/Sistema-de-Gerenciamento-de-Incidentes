CREATE TABLE ativo (
                       id SERIAL PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL,
                       tipo VARCHAR(50) NOT NULL,
                       ip_ou_url VARCHAR(100),
                       criticidade VARCHAR(20) NOT NULL,
                       usuario_id INT,
                       FOREIGN KEY (usuario_id) REFERENCES usuario(codigo)
);