package ru.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "readers")
public class Reader {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String surname;

    @Column(length = 255)
    private String patronymic;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "registration_time", updatable = false)
    private ZonedDateTime registrationTime;

    @Column(name = "phone_number", unique = true, length = 15)
    private String phoneNumber;

    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getSurname() {return surname;}

    public void setSurname(String surname) {this.surname = surname;}

    public String getPatronymic() {return patronymic;}

    public void setPatronymic(String patronymic) {this.patronymic = patronymic;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getBirthDate() {
        if (birthDate != null) {return birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));}
        return null;
    }

    public void setBirthDate(String birthDate) {
        if (birthDate != null && !birthDate.isEmpty()) {
            this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            this.birthDate = null;
        }
    }

    public ZonedDateTime getRegistrationTime() {return registrationTime;}

    public void setRegistrationTime(ZonedDateTime registrationTime) {this.registrationTime = registrationTime;}

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
}