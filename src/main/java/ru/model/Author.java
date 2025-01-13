package ru.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    private UUID id;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false, length = 255)
    private String name;

    private String patronymic;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String biography;

    public void setSurname(String newSurname) {
        this.surname = newSurname;
    }

    // Getters and Setters
}
