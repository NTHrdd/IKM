package ru.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        List<Loan> loans = loanService.getAllLoans(); // Получаем список всех выдач
        model.addAttribute("loans", loans); // Передаем список в модель
        model.addAttribute("loan", new Loan()); // Пустой объект для формы
        model.addAttribute("books", bookService.getAllBooks()); // Список книг
        model.addAttribute("readers", readerService.getAllReaders()); // Список читателей
        return "loans"; // Возвращает шаблон loans.html
    }

    @PostMapping
    public String addLoan(@ModelAttribute Loan loan) {
        loanService.addLoan(loan);
        return "redirect:/loans"; // Перенаправляем на страницу со списком выдач
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable UUID id) {
        try {
            loanService.deleteLoan(id);
            return ResponseEntity.noContent().build(); // Успешное удаление (204 No Content)
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Выдача не найдена (404 Not Found)
        }
    }

    @GetMapping("/edit/{id}")
    public String editLoanForm(@PathVariable UUID id, Model model) {
        Loan loan = loanService.getLoanById(id);
        model.addAttribute("loan", loan); // Передаем выдачу в шаблон
        model.addAttribute("books", bookService.getAllBooks()); // Список книг
        model.addAttribute("readers", readerService.getAllReaders()); // Список читателей
        return "edit-loan"; // Возвращаем шаблон для редактирования выдачи
    }

    @PostMapping("/edit/{id}")
    public String updateLoan(@PathVariable UUID id, @ModelAttribute Loan loan) {
        loanService.updateLoan(
                id,
                loan.getBookId(), // ID книги
                loan.getReaderId(), // ID читателя
                loan.getLoanDate(), // Дата выдачи
                loan.getPenalty(), // Штраф
                loan.getLoanDuration(), // Продолжительность выдачи
                loan.getStatus() // Статус
        );
        return "redirect:/loans"; // Перенаправляем на страницу со списком выдач
    }
}