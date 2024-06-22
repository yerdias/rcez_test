package com.example.controller;

import com.example.payload.PersonDto;
import com.example.model.PersonModel;
import com.example.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;

    @PostMapping("/save")
    private ResponseEntity<PersonModel> save(@RequestBody PersonDto personDTO){
        return ResponseEntity.ok(personService.save(personDTO));
    }

    @GetMapping("/list")
    private ResponseEntity<List<PersonModel>> getListPersons(){
        return ResponseEntity.ok(personService.getListPersons());
    }

    @GetMapping("/{id}")
    private ResponseEntity<PersonModel> findPersonById(@PathVariable UUID id){
        PersonModel person = personService.findPersonById(id);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable UUID id, @RequestBody PersonDto personDTO) {
        PersonDto updatedPerson = personService.updatePerson(id, personDTO);
        return ResponseEntity.ok(updatedPerson);
    }
}
