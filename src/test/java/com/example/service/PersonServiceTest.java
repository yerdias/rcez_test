package com.example.service;

import com.example.entity.Person;
import com.example.exceptions.ResourceNotFoundException;
import com.example.model.PersonModel;
import com.example.payload.PersonDto;
import com.example.repository.PersonRepository;
import com.example.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PersonService personService;

    private Person person;
    private PersonDto personDto;
    private PersonModel personModel;

    @BeforeEach
    void setUp() {
        UUID id = UUID.randomUUID();
        person = new Person();
        person.setId(id);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthDate(LocalDate.of(1990, 1, 1));

        personDto = new PersonDto();
        personDto.setFirstName("John");
        personDto.setLastName("Doe");
        personDto.setBirthDate(LocalDate.of(1990, 1, 1));

        personModel = new PersonModel(id, "Doe", "John", LocalDate.of(1990, 1, 1));
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(PersonDto.class), eq(Person.class))).thenReturn(person);
        when(modelMapper.map(any(Person.class), eq(PersonModel.class))).thenReturn(personModel);
        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonModel result = personService.save(personDto);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testGetListPersons() {
        List<Person> personList = new ArrayList<>();
        personList.add(person);

        when(personRepository.findAll()).thenReturn(personList);
        when(modelMapper.map(any(Person.class), eq(PersonModel.class))).thenReturn(personModel);

        List<PersonModel> result = personService.getListPersons();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testFindPersonById() {
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.of(person));
        when(modelMapper.map(any(Person.class), eq(PersonModel.class))).thenReturn(personModel);

        PersonModel result = personService.findPersonById(person.getId());

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(personRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void testFindPersonById_NotFound() {
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> personService.findPersonById(person.getId()));
        verify(personRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void testUpdatePerson() {
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDto updatedDto = new PersonDto();
        updatedDto.setFirstName("Jane");
        updatedDto.setLastName("Doe");
        updatedDto.setBirthDate(LocalDate.of(1990, 1, 1));

        PersonDto result = personService.updatePerson(person.getId(), updatedDto);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        verify(personRepository, times(1)).findById(any(UUID.class));
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testUpdatePerson_NotFound() {
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        PersonDto updatedDto = new PersonDto();
        updatedDto.setFirstName("Jane");
        updatedDto.setLastName("Doe");
        updatedDto.setBirthDate(LocalDate.of(1990, 1, 1));

        assertThrows(ResourceNotFoundException.class, () -> personService.updatePerson(person.getId(), updatedDto));
        verify(personRepository, times(1)).findById(any(UUID.class));
    }
}

