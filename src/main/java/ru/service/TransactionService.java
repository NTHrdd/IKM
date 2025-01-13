package ru.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.model.Book;

import java.util.UUID;

@Service
public class TransactionService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void performTransaction() {
        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Transactional Book");
        entityManager.persist(book);

        // Создаем точку сохранения
        entityManager.flush();
        entityManager.clear();

        try {
            // Попытка изменения с ошибкой
            book.setTitle(null); // Ошибка валидации
            entityManager.merge(book);
        } catch (Exception e) {
            // Откат изменений
            entityManager.clear();
        }

        // Финальное сохранение
        book.setTitle("Final Title");
        entityManager.merge(book);
    }
}
