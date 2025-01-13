package ru.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.model.Book;
import ru.service.BookService;
import ru.service.GenreService;

import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;

    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @GetMapping
    public String getBooksPage(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("book", new Book()); // Передаем пустой объект Book для формы
        model.addAttribute("genres", genreService.getAllGenres());
        return "books"; // Возвращает шаблон books.html
    }

    @PostMapping
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/books"; // Перенаправляем на страницу со списком книг
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build(); // Успешное удаление (204 No Content)
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Книга не найдена (404 Not Found)
        }
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable UUID id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book); // Передаем книгу в шаблон
        model.addAttribute("genres", genreService.getAllGenres()); // Передаем список жанров
        return "edit-book"; // Возвращаем шаблон для редактирования книги
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable UUID id, @ModelAttribute Book book) {
        bookService.updateBook(id, book.getTitle(), book.getDescription(), book.getGenreId(), book.isAvailable(), book.getPublicationDate(), book.getPopularityScore());
        return "redirect:/books"; // Перенаправляем на страницу со списком книг
    }
}