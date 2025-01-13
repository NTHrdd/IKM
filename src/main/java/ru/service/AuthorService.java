package ru.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.model.Author;
import ru.repository.AuthorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Получение всех авторов
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Добавление нового автора
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Обновление данных автора
    @Transactional
    public void updateAuthor(UUID authorId, String newSurname, String newName, String newPatronymic, String newBirthDate, String newBiography) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        author.setSurname(newSurname);
        author.setName(newName);
        author.setPatronymic(newPatronymic);
        author.setBirthDate(newBirthDate);
        author.setBiography(newBiography);
        authorRepository.save(author);
    }

    // Удаление автора
    @Transactional
    public void deleteAuthor(UUID authorId) {
        authorRepository.deleteById(authorId);
    }

    // Получение автора по ID
    public Author getAuthorById(UUID id) {
        return authorRepository.findById(id).orElseThrow();
    }
}