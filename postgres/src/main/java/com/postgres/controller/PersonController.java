package com.postgres.controller;

import com.postgres.entity.Person;
import com.postgres.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value="/persons")
    public ResponseEntity<Object> getPersons(){
        Map<String, Object> map = new HashMap<>();
        try{
            List<Person> listPersons = personService.findAll();
            return new ResponseEntity<Object>(listPersons, HttpStatus.OK);
        }catch(Exception e){
            map.put("mensaje", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/persons")
    public ResponseEntity<Object> createPerson(@RequestBody Person person){
        Map<String, Object> map = new HashMap<>();
        try{
            Person person1 = personService.save(person);
            return new ResponseEntity<Object>(person1, HttpStatus.OK);
        }catch (Exception exception){
            map.put("message", exception.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable Long id){
        Map<String, Object> map = new HashMap<>();
        try{
            Person personaIdentificada = personService.findById(id);
            personService.delete(personaIdentificada);
            map.put("usuario eliminado", personaIdentificada);
            return new ResponseEntity<Object>(map, HttpStatus.OK);
        }catch (Exception exception){
            map.put("message", exception.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Object> updatePerson(@RequestBody Person person, @PathVariable Long id){
        Map<String, Object> map = new HashMap<>();
        try {
            Person personIdentificada = personService.findById(id);

            if (personIdentificada == null) {
                map.put("message", "Persona no encontrada con el id: " + id);
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }

            // Llamada al servicio para actualizar la persona
            Person updatedPerson = personService.update(id, person);

            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        } catch (Exception exception) {
            map.put("message", exception.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
