package com.example.service;

import com.example.entity.Person;
import com.example.payload.PersonDto;
import com.example.exceptions.ResourceNotFoundException;
import com.example.model.PersonModel;
import com.example.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    public PersonModel save(PersonDto personDTO){
        Person person = mapToEntity(personDTO);
        personRepository.save(person);
        return mapToModel(person);
    }
    public List<PersonModel> getListPersons() {
        return personRepository.findAll().stream()
                .map(person -> modelMapper.map(person, PersonModel.class))
                .collect(Collectors.toList());
    }
    public PersonModel findPersonById(UUID id){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person", "id", id));
        return mapToModel(person);
    }
    public PersonDto updatePerson(UUID id, PersonDto personDto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person", "id", id));

        if (personDto.getLastName() != null && !personDto.getLastName().isEmpty()) {
            person.setLastName(personDto.getLastName());
        }
        if (personDto.getFirstName() != null && !personDto.getFirstName().isEmpty()) {
            person.setFirstName(personDto.getFirstName());
        }
        if (personDto.getBirthDate() != null) {
            person.setBirthDate(personDto.getBirthDate());
        }

        personRepository.save(person);

        return new PersonDto(person.getFirstName(), person.getLastName(), person.getBirthDate());
    }

    private Person mapToEntity(PersonDto personDto){
        return modelMapper.map(personDto, Person.class);
    }
    private PersonModel mapToModel(Person person){
        return modelMapper.map(person, PersonModel.class);
    }

}
