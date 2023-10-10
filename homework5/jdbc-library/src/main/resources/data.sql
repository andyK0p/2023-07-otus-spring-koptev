INSERT INTO authors(full_name) values ('Joshua Bloch');
INSERT INTO authors(full_name) values ('Bruce Eckel');
INSERT INTO authors(full_name) values ('Jane Austen');

INSERT INTO genres(name) values ('Computer Science');
INSERT INTO genres(name) values ('Romance');

INSERT INTO books(title,author_id,page_count,genre_id)
values ('Effective Java 2nd Edition',1,346,1);
INSERT INTO books(title,author_id,page_count,genre_id)
values ('Thinking in Java 4th Edition',2,1150,1);
INSERT INTO books(title,author_id,page_count,genre_id)
values ('Pride and Prejudice',1,259,2);

