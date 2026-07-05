-- Esquema de base de datos para Biblioteca Backend
-- Base de datos: H2
-- Proyecto: Gestión de libros y autores

DROP TABLE IF EXISTS libros;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS autores;

CREATE TABLE autores (
                         id VARCHAR(36) NOT NULL,
                         nombre VARCHAR(100) NOT NULL,
                         fecha_nacimiento DATE,
                         CONSTRAINT pk_autores PRIMARY KEY (id)
);

CREATE TABLE libros (
                        id VARCHAR(36) NOT NULL,
                        titulo VARCHAR(150) NOT NULL,
                        isbn VARCHAR(20) NOT NULL,
                        autor_id VARCHAR(36) NOT NULL,
                        numero_paginas INT NOT NULL,
                        url_portada VARCHAR(500),
                        CONSTRAINT pk_libros PRIMARY KEY (id),
                        CONSTRAINT fk_libros_autores
                            FOREIGN KEY (autor_id)
                                REFERENCES autores(id)
                                ON DELETE CASCADE
);

CREATE TABLE usuarios (
                          id VARCHAR(36) NOT NULL,
                          username VARCHAR(100) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          CONSTRAINT pk_usuarios PRIMARY KEY (id),
                          CONSTRAINT uk_usuarios_username UNIQUE (username)
);

-- Usuario inicial de prueba
INSERT INTO usuarios (id, username, password)
VALUES ('1', 'admin', '123456');