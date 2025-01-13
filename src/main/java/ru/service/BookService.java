package ru.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.model.Book;
import ru.repository.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void updateBook(UUID bookId, String newTitle, String newDescription, Integer newGenreId, boolean newIsAvailable, String newPublicationDate, float newPopularityScore) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setTitle(newTitle);
        book.setDescription(newDescription);
        book.setGenreId(newGenreId);
        book.setAvailable(newIsAvailable);
        book.setPublicationDate(newPublicationDate);
        book.setPopularityScore(newPopularityScore);
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(UUID bookId) {
        bookRepository.deleteById(bookId);
    }

    public Book getBookById(UUID id) {
        return bookRepository.findById(id).orElseThrow();
    }
}
