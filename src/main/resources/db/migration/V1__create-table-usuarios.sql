create table usuarios (
    id bigint not null auto_increment primary key,
    apelido varchar(32) not null unique,
    nome varchar(32) not null,
    sobrenome varchar(255) not null,
    email varchar(255) not null unique,
    senha varchar(255) not null,
    data_de_nascimento date not null,
    cidade varchar(100) not null,
    estado varchar(100) not null,
    pais varchar(100) not null,
    telefone varchar(20),
    esta_validado boolean
);