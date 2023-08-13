package org.logiware.rest.client.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.logiware.rest.client.example.domain.Person;

import java.util.List;
import java.util.Optional;

@Controller("/person")
public class PersonController {
    @Inject
    PersonService personService;

    @Post
    public Person add(@Body Person person) {
        return personService.add(person);
    }

    @Get("/{id}")
    public Optional<Person> findById(@PathVariable Integer id) {
        return personService.getPersonList().stream()
                .filter(person -> person.id().equals(id))
                .findFirst();
    }

    @Get(value = "all", produces = MediaType.APPLICATION_JSON)
    public List<Person> findAll() {
        return personService.getPersonList();
    }
}