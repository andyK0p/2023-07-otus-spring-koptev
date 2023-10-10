DROP TABLE IF EXISTS authors;
CREATE TABLE authors(
    id bigint auto_increment,
    full_name varchar(255),
    primary key (id)
);

DROP TABLE IF EXISTS genres;
CREATE TABLE genres(
    id bigint auto_increment,
    name varchar(255),
    primary key (id)
);

DROP TABLE IF EXISTS books;
CREATE TABLE books(
    id bigint auto_increment,
    title varchar(255),
    author_id bigint references authors(id) on delete cascade,
    page_count int,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
);