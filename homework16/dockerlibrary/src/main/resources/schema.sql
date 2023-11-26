DROP TABLE IF EXISTS authors CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS books CASCADE;


CREATE TABLE authors(
    id bigserial not null primary key,
    full_name varchar(255)
);

CREATE TABLE genres(
    id bigserial not null primary key,
    name varchar(255)
);

CREATE TABLE books(
    id bigserial primary key,
    title varchar(255),
    page_count int,
    author_id bigint references authors(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade
);

CREATE TABLE comments(
    id bigserial not null primary key,
    name varchar(255),
    text varchar(800),
    book_id bigint references books(id) on delete cascade
);