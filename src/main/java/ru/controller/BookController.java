package ru.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.model.Book;
import ru.service.BookService;
import ru.service.GenreService;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final GenreService genreService;

    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @GetMapping
    public String getBooksPage(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("book", new Book());
        model.addAttribute("genres", genreService.getAllGenres());
        return "books";
    }

    @PostMapping
    public String addBook(@Valid @ModelAttribute Book book, BindingResult bindingResult, Model model) {
        logger.info("Attempting to add a new book: {}", book);
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors occurred: {}", bindingResult.getAllErrors());
            model.addAttribute("genres", genreService.getAllGenres());
            return "books";
        }
        try {
            bookService.addBook(book);
            logger.info("Book added successfully: {}", book);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database constraint violation: {}", e.getMessage());
            model.addAttribute("error", "Database error: " + Objects.requireNonNull(e.getRootCause()).getMessage()); // Используем getRootCause() для получения конкретной ошибки
            model.addAttribute("genres", genreService.getAllGenres());
            return "books";
        }
        return "redirect:/books";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable UUID id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("genres", genreService.getAllGenres());
        return "edit-book";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable UUID id, @Valid @ModelAttribute Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.getAllGenres());
            return "edit-book";
        }
        bookService.updateBook(id, book.getTitle(), book.getDescription(), book.getGenreId(), book.isAvailable(), book.getPublicationDate(), book.getPopularityScore());
        return "redirect:/books";
    }

    @PostMapping("/complex-operation")
    public ResponseEntity<Void> performComplexOperation() {
        bookService.complexBookOperation();
        return ResponseEntity.ok().build();
    }
}