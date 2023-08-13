package org.logiware.rest.client.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import org.logiware.rest.client.example.domain.Person;

import java.util.List;
import java.util.Optional;

@Client("/person")
public interface PersonClient {
    @Post
    Person add(@Body Person person);

    @Get("{id}")
    Optional<Person> findById(@PathVariable Integer id);

    @Get(value = "all", consumes = MediaType.APPLICATION_JSON)
    List<Person> findAll();

}
