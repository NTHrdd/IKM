package ru.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotBlank(message = "Surname is mandatory")
    @Size(max = 50, message = "Surname must be less than 50 characters")
    @Column(nullable = false, length = 50)
    private String surname;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name must be less than 255 characters")
    @Column(nullable = false, length = 255)
    private String name;

    @Size(max = 255, message = "Patronymic must be less than 255 characters")
    private String patronymic;

    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Size(max = 1000, message = "Biography must be less than 1000 characters")
    private String biography;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    public UUID getId() {return id;}

    public String getSurname() {return surname;}

    public String getName() {return name;}

    public String getPatronymic() {return patronymic;}

    public LocalDate getBirthDate() {return birthDate;}

    public String getBiography() {return biography;}

    public void setId(UUID id) {this.id = id;}

    public void setSurname(String surname) {this.surname = surname;}

    public void setName(String name) {this.name = name;}

    public void setPatronymic(String patronymic) {this.patronymic = patronymic;}

    public void setBirthDate(LocalDate birthDate) {this.birthDate = birthDate;}

    public void setBiography(String biography) {this.biography = biography;}

    public Set<Book> getBooks() {return books;}

    public void setBooks(Set<Book> books) {this.books = books;}
}