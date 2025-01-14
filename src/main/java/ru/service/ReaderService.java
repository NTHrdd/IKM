package ru.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.model.Reader;
import ru.repository.ReaderRepository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {this.readerRepository = readerRepository;}

    public List<Reader> getAllReaders() {return readerRepository.findAll();}

    public Reader addReader(Reader reader) {
        reader.setRegistrationTime(ZonedDateTime.now());
        return readerRepository.save(reader);
    }

    @Transactional
    public void updateReader(UUID readerId, String newName, String newSurname, String newPatronymic, String newEmail, LocalDate newBirthDate, String newPhoneNumber) {
        Reader reader = readerRepository.findById(readerId).orElseThrow();
        reader.setName(newName);
        reader.setSurname(newSurname);
        reader.setPatronymic(newPatronymic);
        reader.setEmail(newEmail);
        reader.setBirthDate(newBirthDate);
        reader.setPhoneNumber(newPhoneNumber);
        readerRepository.save(reader);
    }

    @Transactional
    public void deleteReader(UUID readerId) {
        readerRepository.deleteById(readerId);
    }

    public Reader getReaderById(UUID id) {
        return readerRepository.findById(id).orElseThrow();
    }

    public Reader findById(UUID readerId) {return readerRepository.findById(readerId).orElseThrow();}
}