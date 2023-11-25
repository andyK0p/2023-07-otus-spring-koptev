package ru.otus.spring.datalibrary.mappers;

import ru.otus.spring.datalibrary.data.entity.Book;
import ru.otus.spring.datalibrary.dto.BookDto;

public class BookMapper {

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getPageCount(),
                AuthorMapper.toDto(book.getAuthor()), GenreMapper.toDto(book.getGenre()));
    }

    public static Book toDomainObject(BookDto dto) {
        return new Book(dto.getId(), dto.getTitle(), dto.getPageCount(),
                AuthorMapper.toDomainObject(dto.getAuthor()), GenreMapper.toDomainObject(dto.getGenre()));
    }
}
