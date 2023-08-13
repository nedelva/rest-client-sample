package org.logiware.rest.client.example;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.logiware.rest.client.example.domain.Person;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Controller("/personReactive")
public class PersonReactiveController {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PersonReactiveController.class);

    @Inject
    PersonService personService;

    @Post
    public Mono<Person> add(@Body Person person) {
        Person newPerson = personService.add(person);
        return Mono.just(newPerson); // (1)
    }

    @Get("/{id}")
    public Publisher<Optional<Person>> findById(@PathVariable Integer id) {
        return Publishers.just(personService.getPersonList()
                .stream()
                .filter(person -> person.id().equals(id))
                .findAny()
        ); // (2)
    }

    @Get(value = "stream", produces = MediaType.APPLICATION_JSON_STREAM)
    public Flux<Person> findAllStream() {
        return Flux.fromIterable(personService.getPersonList())
                .doOnNext(person -> LOGGER.info("Server: {}", person)); // (3)
    }
}