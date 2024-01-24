package com.postgres.service;

import com.postgres.entity.Person;
import com.postgres.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person update(Long id, Person updatedPerson) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con el id: " + id));

        // Realizar validaciones específicas del dominio aquí, por ejemplo:
        if (updatedPerson.getName() == null || updatedPerson.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        // Actualizar los campos de la persona existente con los nuevos valores
        existingPerson.setName(updatedPerson.getName());
        existingPerson.setAddress(updatedPerson.getAddress());
        existingPerson.setPhone(updatedPerson.getPhone());

        // Llamada al repositorio para guardar los cambios
        return personRepository.save(existingPerson);
    }
    @Override
    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
    }
}
