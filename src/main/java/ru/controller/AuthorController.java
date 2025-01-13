package ru.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.model.Author;
import ru.service.AuthorService;

import java.util.UUID;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {this.authorService = authorService;}

    @GetMapping
    public String getAuthorsPage(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("author", new Author());
        return "authors";
    }

    @PostMapping
    public String addAuthor(@ModelAttribute Author author) {
        authorService.addAuthor(author);
        return "redirect:/authors";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/edit/{id}")
    public String editAuthorForm(@PathVariable UUID id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "edit-author";
    }

    @PostMapping("/edit/{id}")
    public String updateAuthor(@PathVariable UUID id, @ModelAttribute Author author) {
        authorService.updateAuthor(id, author.getSurname(), author.getName(), author.getPatronymic(), author.getBirthDate(), author.getBiography());
        return "redirect:/authors";
    }
}