package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Reader;

import java.util.UUID;

public interface ReaderRepository extends JpaRepository<Reader, UUID> {
}