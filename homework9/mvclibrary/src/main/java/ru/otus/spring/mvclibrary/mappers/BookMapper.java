package ru.otus.spring.mvclibrary.mappers;

import ru.otus.spring.mvclibrary.data.entity.Book;
import ru.otus.spring.mvclibrary.dto.BookDto;
import ru.otus.spring.mvclibrary.dto.input.BookInputDto;

public class BookMapper {

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getPageCount(),
                AuthorMapper.toDto(book.getAuthor()), GenreMapper.toDto(book.getGenre()));
    }

    public static Book toDomainObject(BookDto dto) {
        return new Book(dto.getId(), dto.getTitle(), dto.getPageCount(),
                AuthorMapper.toDomainObject(dto.getAuthor()), GenreMapper.toDomainObject(dto.getGenre()));
    }

    public static BookInputDto toInputDto(BookDto dto) {
        return new BookInputDto(dto.getId(), dto.getTitle(), dto.getPageCount(),
                dto.getAuthor().getId(), dto.getGenre().getId());
    }
}
