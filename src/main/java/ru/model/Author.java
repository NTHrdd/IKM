package ru.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false, length = 255)
    private String name;

    private String patronymic;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String biography;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    public UUID getId() {return id;}

    public String getSurname() {return surname;}

    public String getName() {return name;}

    public String getPatronymic() {return patronymic;}

    public String getBirthDate() {
        if (birthDate != null) {return birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));}
        return null;
    }

    public String getBiography() {return biography;}

    public void setId(UUID id) {this.id = id;}

    public void setSurname(String surname) {this.surname = surname;}

    public void setName(String name) {this.name = name;}

    public void setPatronymic(String patronymic) {this.patronymic = patronymic;}

    public void setBirthDate(String birthDate) {
        if (birthDate != null && !birthDate.isEmpty()) {
            this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            this.birthDate = null;
        }
    }

    public void setBiography(String biography) {this.biography = biography;}

    public Set<Book> getBooks() {return books;}

    public void setBooks(Set<Book> books) {this.books = books;}
}