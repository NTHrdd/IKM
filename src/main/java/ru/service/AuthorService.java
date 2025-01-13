package ru.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.model.Author;
import ru.repository.AuthorRepository;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public void updateAuthor(UUID authorId, String newSurname) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        author.setSurname(newSurname);
        authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(UUID authorId) {
        authorRepository.deleteById(authorId);
    }
}
