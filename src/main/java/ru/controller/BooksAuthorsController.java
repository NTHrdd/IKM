package ru.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.model.Book;
import ru.model.Author;
import ru.repository.BookRepository;
import ru.repository.AuthorRepository;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/book-authors")
public class BooksAuthorsController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BooksAuthorsController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public String getAllBookAuthors(Model model) {
        List<Book> books = bookRepository.findAll();
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("authors", authors);
        return "book-authors";
    }

    @PostMapping("/add")
    public String addBookAuthor(@RequestParam UUID bookId, @RequestParam UUID authorId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));
        book.getAuthors().add(author);
        bookRepository.save(book);
        return "redirect:/book-authors";
    }

    @PostMapping("/delete")
    public String deleteBookAuthor(@RequestParam UUID bookId, @RequestParam UUID authorId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));
        book.getAuthors().remove(author);
        bookRepository.save(book);
        return "redirect:/book-authors";
    }
}