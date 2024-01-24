package com.postgres.service;

import com.postgres.entity.Person;

import java.util.List;

public interface PersonService {

    public List<Person> findAll();
    public Person save(Person person);
    public Person update(Long id, Person person);
    public Person findById(Long id);
    public void delete(Person person);
}
