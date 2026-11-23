CREATE DATABASE ingresso;

USE ingresso;

CREATE TABLE eventos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    capacidade INT NOT NULL
);

CREATE TABLE vendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    evento_id INT NOT NULL,
    comprador VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL,
    FOREIGN KEY (evento_id) REFERENCES eventos(id)
);
