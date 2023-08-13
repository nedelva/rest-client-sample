package org.logiware.rest.client.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import org.logiware.rest.client.example.domain.Person;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Client("/personReactive")
public interface PersonReactiveClient {
    @Post
    Mono<Person> add(@Body Person person);

    @Get("{id}")
    Publisher<Person> findById(@PathVariable Integer id);

    @Get(value = "stream", consumes = MediaType.APPLICATION_JSON_STREAM)
    Flux<Person> findAllStream();

}
