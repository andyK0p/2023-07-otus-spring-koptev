INSERT INTO authors(full_name) values ('Joshua Bloch'), ('Bruce Eckel'), ('Jane Austen'), ('Mark Twain');

INSERT INTO genres(name) values ('Computer Science'), ('Romance'), ('Adventures');

INSERT INTO books(title,page_count,author_id,genre_id)
values ('Effective Java 2nd Edition',346,1,1), ('Thinking in Java 4th Edition',1150,2,1), ('Pride and Prejudice',259,3,2),
       ('The Adventures of Tom Sawyer', 168,4,3), ('Emma', 218,3,2), ('The Adventures of Huckleberry Finn', 224,4,3);

INSERT INTO comments(name,text,book_id)
values ('Andy','This is some very long book!',1), ('Jane','I absolutely love this romantic book!',3),
       ('Andy','No, this is boring!',3), ('Deenash','Overall, the book is great for beginners.',2),
       ('Andy','Nice to start learning Java with this book. I recommend!', 2), ('Alex','Cool!',1),
       ('Martha','Enjoyed reading it',5), ('Andy','This is classic!',4), ('Mike','Huck Finn for the win!',6)