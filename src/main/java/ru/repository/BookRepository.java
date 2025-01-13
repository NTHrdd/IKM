package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Book;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
