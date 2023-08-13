package org.logiware.rest.client.example;

import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.logiware.rest.client.example.domain.Person;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@MicronautTest
public class PersonReactiveControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonReactiveControllerTest.class);

    @Inject
    EmbeddedServer server;
    @Inject
    PersonReactiveClient client;


    @Test
    public void testAddDeclarative() {
        final AtomicBoolean flip = new AtomicBoolean(false);
        final Person person = new Person(null, "First Last", 22);
        Mono<Person> s = client.add(person);
        s.subscribe(person1 -> {
            LOGGER.info("Added: {}", person1);
            assertThat(person1.id(), is(3));
            flip.set(true);
        });
        await().atMost(200, TimeUnit.MILLISECONDS).until(flip::get); // (1)

    }

    @Test
    void testFindByIdDeclarative() {
        final AtomicBoolean flip = new AtomicBoolean(false);
        Publisher<Person> publisher = client.findById(1);
        publisher.subscribe(new Subscriber<Person>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);  //(2)
            }

            @Override
            public void onNext(Person person) {
                LOGGER.info("Received a person: {}", person);
                assertThat(person.id(), is(1)); // (3)
            }

            @Override
            public void onError(Throwable t) {
                LOGGER.error("Something went wrong", t);
            }

            @Override
            public void onComplete() {
                flip.set(true); // (4)
            }
        });
        await().atMost(300, TimeUnit.MILLISECONDS).until(flip::get);
    }

    @Test
    void testFindAllStreamDeclarative() {
        final AtomicBoolean flip = new AtomicBoolean(false);
        Flux<Person> personFlux = client.findAllStream();
        personFlux.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(2);
            }

            @Override
            public void onNext(Person person) {
                LOGGER.info("Received a person: {}", person);
            }

            @Override
            public void onError(Throwable t) {
                LOGGER.error("Something went wrong", t);
            }

            @Override
            public void onComplete() {
                flip.set(true);
            }
        });
        await().atMost(300, TimeUnit.MILLISECONDS).until(flip::get);
    }

    @Test
    void testFindAllStreamDeclarationUsingCollectList() {
        final AtomicBoolean flip = new AtomicBoolean(false);
        Flux<Person> personFlux = client.findAllStream();
        Mono<List<Person>> listMono = personFlux.collectList().doFinally(c -> LOGGER.info("All items were processed."));
        listMono.subscribe( (List<Person> personList) -> flip.set(true) );
        await().atMost(300, TimeUnit.MILLISECONDS).until(flip::get);
    }
}
