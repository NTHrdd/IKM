package ru.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @Column(name = "reader_id", nullable = false)
    private UUID readerId;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "penalty")
    private int penalty;

    @Column(name = "loan_duration")
    private String loanDuration;

    @Column(name = "status", nullable = false)
    private String status = "active";

    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}

    public UUID getBookId() {return bookId;}

    public void setBookId(UUID bookId) {this.bookId = bookId;}

    public UUID getReaderId() {return readerId;}

    public void setReaderId(UUID readerId) {this.readerId = readerId;}

    public String getLoanDate() {
        if (loanDate != null) {return loanDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));}
        return null;
    }

    public void setLoanDate(String loanDate) {
        if (loanDate != null && !loanDate.isEmpty()) {
            this.loanDate = LocalDate.parse(loanDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            this.loanDate = null;
        }
    }

    public int getPenalty() {return penalty;}

    public void setPenalty(int penalty) {this.penalty = penalty;}

    public String getLoanDuration() {return loanDuration;}

    public void setLoanDuration(String loanDuration) {this.loanDuration = loanDuration;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}
}