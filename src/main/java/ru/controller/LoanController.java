package ru.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.model.Loan;
import ru.service.LoanService;
import ru.service.BookService;
import ru.service.ReaderService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;
    private final BookService bookService;
    private final ReaderService readerService;

    public LoanController(LoanService loanService, BookService bookService, ReaderService readerService) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @GetMapping
    public String getLoansPage(Model model) {
        List<Loan> loans = loanService.getAllLoans();
        model.addAttribute("loans", loans);
        model.addAttribute("loan", new Loan());
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("readers", readerService.getAllReaders());
        return "loans";
    }

    @PostMapping
    public String addLoan(@Valid @ModelAttribute Loan loan, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("readers", readerService.getAllReaders());
            return "loans";
        }
        loanService.addLoan(loan);
        return "redirect:/loans";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable UUID id) {
        try {
            loanService.deleteLoan(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/edit/{id}")
    public String editLoanForm(@PathVariable UUID id, Model model) {
        Loan loan = loanService.getLoanById(id);
        model.addAttribute("loan", loan);
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("readers", readerService.getAllReaders());
        return "edit-loan";
    }

    @PostMapping("/edit/{id}")
    public String updateLoan(@PathVariable UUID id, @Valid @ModelAttribute Loan loan, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("readers", readerService.getAllReaders());
            return "edit-loan";
        }
        loanService.updateLoan(id, loan.getBookId(), loan.getReaderId(), loan.getLoanDate(), loan.getPenalty(), loan.getLoanDuration(), loan.getStatus());
        return "redirect:/loans";
    }
}