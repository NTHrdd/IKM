package ru.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.model.Reader;
import ru.repository.ReaderRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    // Получение всех читателей
    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    // Добавление нового читателя
    public Reader addReader(Reader reader) {
        reader.setRegistrationTime(ZonedDateTime.now()); // Устанавливаем текущее время регистрации
        return readerRepository.save(reader);
    }

    // Обновление данных читателя
    @Transactional
    public void updateReader(UUID readerId, String newName, String newSurname, String newPatronymic, String newEmail, String newBirthDate, String newPhoneNumber) {
        Reader reader = readerRepository.findById(readerId).orElseThrow();
        reader.setName(newName);
        reader.setSurname(newSurname);
        reader.setPatronymic(newPatronymic);
        reader.setEmail(newEmail);
        reader.setBirthDate(newBirthDate);
        reader.setPhoneNumber(newPhoneNumber);
        readerRepository.save(reader);
    }

    // Удаление читателя
    @Transactional
    public void deleteReader(UUID readerId) {
        readerRepository.deleteById(readerId);
    }

    // Получение читателя по ID
    public Reader getReaderById(UUID id) {
        return readerRepository.findById(id).orElseThrow();
    }
}