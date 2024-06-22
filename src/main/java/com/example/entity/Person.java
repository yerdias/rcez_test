package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    private UUID id;

    private String lastName;

    private String firstName;

    private LocalDate birthDate;

    public Person() {
        this.id = UUID.randomUUID();
    }
}
