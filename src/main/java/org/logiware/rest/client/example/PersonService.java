package org.logiware.rest.client.example;


import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import org.logiware.rest.client.example.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class PersonService {

    AtomicInteger counter = new AtomicInteger();
    List<Person> people = new ArrayList<>();

    @PostConstruct
    void init() {
        people.add(new Person(1, "John Doe", 22));
        people.add(new Person(2, "Jane Doe", 20));
        counter.set(2);
    }

    public Integer nextId() {
        return counter.incrementAndGet();
    }

    public Person add(Person person) {
        String name = person.name();
        Integer age = person.age();
        Person newPerson = new Person(nextId(), name, age);
        people.add(newPerson);
        return newPerson;
    }

    public List<Person> getPersonList() {
        return List.copyOf(people);
    }
}
