package ru.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false, length = 255)
    private String title;

    private String description;

    @Column(name = "genre_id")
    private Integer genreId;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "popularity_score")
    private Float popularityScore;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Integer getGenreId() {return genreId;}

    public void setGenreId(Integer genreId) {this.genreId = genreId;}

    public Boolean getIsAvailable() {return isAvailable;}

    public void setIsAvailable(Boolean isAvailable) {this.isAvailable = isAvailable;}

    public String getPublicationDate() {
        if (publicationDate != null) {return publicationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));}
        return null;
    }

    public void setPublicationDate(LocalDate publicationDate) {this.publicationDate = publicationDate;}

    public void setPublicationDate(String publicationDate) {
        if (publicationDate != null && !publicationDate.isEmpty()) {
            this.publicationDate = LocalDate.parse(publicationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            this.publicationDate = null;
        }
    }

    public Float getPopularityScore() {return popularityScore;}

    public void setPopularityScore(Float popularityScore) {this.popularityScore = popularityScore;}

    public String getCreatedAt() {
        if (createdAt != null) {return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));}
        return null;
    }

    @PrePersist
    protected void onCreate() {this.createdAt = LocalDate.now();}

    public boolean isAvailable() {return isAvailable;}

    public void setAvailable(boolean newIsAvailable) {this.isAvailable = newIsAvailable;}

    public Set<Author> getAuthors() {return authors;}

    public void setAuthors(Set<Author> authors) {this.authors = authors;}
}