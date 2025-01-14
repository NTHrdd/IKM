package ru.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotNull(message = "Book ID is mandatory")
    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @NotNull(message = "Reader ID is mandatory")
    @Column(name = "reader_id", nullable = false)
    private UUID readerId;

    @NotNull(message = "Loan date is mandatory")
    @PastOrPresent(message = "Loan date must be in the past or present")
    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Min(value = 0, message = "Penalty must be at least 0")
    @Column(name = "penalty")
    private int penalty;

    @Size(max = 50, message = "Loan duration must be less than 50 characters")
    @Column(name = "loan_duration")
    private String loanDuration;

    @NotBlank(message = "Status is mandatory")
    @Size(max = 20, message = "Status must be less than 20 characters")
    @Column(name = "status", nullable = false)
    private String status = "active";

    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}

    public UUID getBookId() {return bookId;}

    public void setBookId(UUID bookId) {this.bookId = bookId;}

    public UUID getReaderId() {return readerId;}

    public void setReaderId(UUID readerId) {this.readerId = readerId;}

    public LocalDate getLoanDate() {return loanDate;}

    public void setLoanDate(LocalDate loanDate) {this.loanDate = loanDate;}

    public int getPenalty() {return penalty;}

    public void setPenalty(int penalty) {this.penalty = penalty;}

    public String getLoanDuration() {return loanDuration;}

    public void setLoanDuration(String loanDuration) {this.loanDuration = loanDuration;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}
}