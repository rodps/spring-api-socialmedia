create table posts (
    id bigint not null auto_increment primary key,
    media_url varchar(255) not null,
    texto varchar(255),
    data_de_criacao date not null,
    autor_id bigint not null,
    foreign key (autor_id) references usuarios(id)
);