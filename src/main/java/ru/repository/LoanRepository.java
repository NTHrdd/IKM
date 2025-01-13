package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Loan;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
}