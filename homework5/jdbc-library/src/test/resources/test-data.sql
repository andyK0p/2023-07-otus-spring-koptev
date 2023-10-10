INSERT INTO authors(full_name) values ('Frank Herbert'), ('Charlotte Bronte'), ('Herman Melville');

INSERT INTO genres(name) values ('Sci-Fi'), ('Romance'), ('Adventures');

INSERT INTO books(title,author_id,page_count,genre_id)
values ('Dune',1,896,1),
       ('Jane Ayre',2,624,2),
       ('Moby Dick',3,378,3);


