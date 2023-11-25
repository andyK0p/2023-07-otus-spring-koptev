DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS books;


CREATE TABLE authors(
    id bigint auto_increment,
    full_name varchar(255),
    primary key (id)
);

CREATE TABLE genres(
    id bigint auto_increment,
    name varchar(255),
    primary key (id)
);

CREATE TABLE books(
    id bigint auto_increment,
    title varchar(255),
    page_count int,
    author_id bigint references authors(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
);

CREATE TABLE comments(
    id bigint auto_increment,
    name varchar(255),
    text varchar(800),
    book_id bigint references books(id) on delete cascade,
    primary key (id)
);