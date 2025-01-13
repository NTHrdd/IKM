package ru.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.model.Loan;
import ru.repository.LoanRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class LoanService {
    private final LoanRepository loanRepository;


    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan addLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Transactional
    public void updateLoan(UUID loanId, UUID newBookId, UUID newReaderId, String newLoanDate, int newPenalty, String newLoanDuration, String newStatus) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
        loan.setBookId(newBookId); // Предполагается, что bookService доступен
        loan.setReaderId(newReaderId); // Предполагается, что readerService доступен
        loan.setLoanDate(newLoanDate);
        loan.setPenalty(newPenalty);
        loan.setLoanDuration(newLoanDuration);
        loan.setStatus(newStatus);
        loanRepository.save(loan);
    }

    @Transactional
    public void deleteLoan(UUID loanId) {
        loanRepository.deleteById(loanId);
    }

    public Loan getLoanById(UUID id) {
        return loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
    }
}