package ru.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import ru.model.Book;
import ru.repository.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public BookService(BookRepository bookRepository, TransactionTemplate transactionTemplate) {
        this.bookRepository = bookRepository;
        this.transactionTemplate = transactionTemplate;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book addBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Invalid data: " + e.getMessage());
        }
    }

    @Transactional
    public void updateBook(UUID bookId, String newTitle, String newDescription, Integer newGenreId, boolean newIsAvailable, LocalDate newPublicationDate, float newPopularityScore) {
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
    public void deleteBook(UUID bookId) {bookRepository.deleteById(bookId);}

    public Book getBookById(UUID id) {return bookRepository.findById(id).orElseThrow();}

    public Book findById(UUID bookId) {return bookRepository.findById(bookId).orElseThrow();}

    public void complexBookOperation() {
        logger.info("Starting complex book operation...");
        transactionTemplate.execute(status -> {
            logger.info("Savepoint created.");
            status.setRollbackOnly();
            logger.info("Rolled back to savepoint.");

            return null;
        });
        transactionTemplate.execute(status -> {
            logger.info("New transaction started.");
            Book book1 = new Book();
            book1.setTitle("New Book 1");
            book1.setDescription("Description of New Book 1");
            book1.setGenreId(1);
            book1.setAvailable(true);
            book1.setPublicationDate(LocalDate.now());
            book1.setPopularityScore(5.0f);
            bookRepository.save(book1);
            logger.info("Created and saved new book 1.");
            bookRepository.flush();
            logger.info("Flushed the table.");
            Book book2 = new Book();
            book2.setTitle("New Book 2");
            book2.setDescription("Description of New Book 2");
            book2.setGenreId(2);
            book2.setAvailable(true);
            book2.setPublicationDate(LocalDate.now());
            book2.setPopularityScore(4.5f);
            bookRepository.save(book2);
            logger.info("Created and saved new book 2.");
            status.setRollbackOnly();
            logger.info("Transaction marked for rollback.");
            return null;
        });

        logger.info("Complex book operation completed.");
    }
}
