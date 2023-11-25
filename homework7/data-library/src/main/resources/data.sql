INSERT INTO authors(full_name) values ('Joshua Bloch'), ('Bruce Eckel'), ('Jane Austen');

INSERT INTO genres(name) values ('Computer Science'), ('Romance'), ('Adventures');

INSERT INTO books(title,page_count,author_id,genre_id)
values ('Effective Java 2nd Edition',346,1,1), ('Thinking in Java 4th Edition',1150,2,1), ('Pride and Prejudice',259,1,2);

INSERT INTO comments(name,text,book_id)
values ('Andy','This is boring!',3)