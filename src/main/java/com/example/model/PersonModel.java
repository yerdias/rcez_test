package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonModel {
    private UUID id;

    private String lastName;

    private String firstName;

    private LocalDate birthDate;
}
