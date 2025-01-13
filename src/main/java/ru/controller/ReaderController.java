package ru.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.model.Reader;
import ru.service.ReaderService;

import java.util.UUID;

@Controller
@RequestMapping("/readers")
public class ReaderController {
    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;}

    @GetMapping
    public String getReadersPage(Model model) {
        model.addAttribute("readers", readerService.getAllReaders());
        model.addAttribute("reader", new Reader());
        return "readers";
    }

    @PostMapping
    public String addReader(@ModelAttribute Reader reader) {
        readerService.addReader(reader);
        return "redirect:/readers";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable UUID id) {
        try {
            readerService.deleteReader(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/edit/{id}")
    public String editReaderForm(@PathVariable UUID id, Model model) {
        Reader reader = readerService.getReaderById(id);
        model.addAttribute("reader", reader);
        return "edit-reader";
    }

    @PostMapping("/edit/{id}")
    public String updateReader(@PathVariable UUID id, @ModelAttribute Reader reader) {
        readerService.updateReader(id, reader.getName(), reader.getSurname(), reader.getPatronymic(), reader.getEmail(), reader.getBirthDate(), reader.getPhoneNumber());
        return "redirect:/readers";
    }
}