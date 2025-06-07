DROP DATABASE IF EXISTS rede_leitura;
CREATE DATABASE rede_leitura;
USE rede_leitura;

-- Tabela de usuários
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    livro_atual_isbn VARCHAR(20), -- Armazena o ISBN do livro atual
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de livros lidos (relaciona o usuário com os dados do livro lido)
CREATE TABLE livros_lidos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    titulo VARCHAR(200),
    autor VARCHAR(100),
    data_leitura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT unica_leitura UNIQUE (usuario_id, isbn)
);

-- Tabela de solicitações de amizade com status
CREATE TABLE solicitacoes_amizade (
    id INT PRIMARY KEY AUTO_INCREMENT,
    solicitante_id INT NOT NULL,
    solicitado_id INT NOT NULL,
    status ENUM('pendente', 'aceita', 'recusada') DEFAULT 'pendente',
    data_solicitacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (solicitante_id) REFERENCES usuarios(id),
    FOREIGN KEY (solicitado_id) REFERENCES usuarios(id),
    CONSTRAINT unica_solicitacao UNIQUE (solicitante_id, solicitado_id),
    CHECK (solicitante_id <> solicitado_id)
);

-- Tabela de amizades (relacionamento bidirecional)
CREATE TABLE amizades (
    usuario_id INT NOT NULL,
    amigo_id INT NOT NULL,
    data_amizade TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (usuario_id, amigo_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (amigo_id) REFERENCES usuarios(id),
    CHECK (usuario_id <> amigo_id)
);

-- Trigger para aceitar solicitação e criar amizade nos dois sentidos
DELIMITER $$

CREATE TRIGGER aceitar_solicitacao
AFTER UPDATE ON solicitacoes_amizade
FOR EACH ROW
BEGIN
    IF OLD.status = 'pendente' AND (NEW.status = 'aceita' OR NEW.status = 'recusada') THEN
        IF NEW.status = 'aceita' THEN
            INSERT IGNORE INTO amizades (usuario_id, amigo_id) VALUES (NEW.solicitante_id, NEW.solicitado_id);
            INSERT IGNORE INTO amizades (usuario_id, amigo_id) VALUES (NEW.solicitado_id, NEW.solicitante_id);
        END IF;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Só é permitido alterar solicitações com status pendente.';
    END IF;
END$$

DELIMITER ;