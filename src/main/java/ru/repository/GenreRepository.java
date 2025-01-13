package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
