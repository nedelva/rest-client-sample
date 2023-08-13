package org.logiware.rest.client.example;

import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.logiware.rest.client.example.domain.Person;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@MicronautTest // (1)
public class PersonControllerTest {

    @Inject
    EmbeddedServer server; // (2)
    @Inject
    PersonClient client;  // (3)

    @Test
    public void testAddDeclarative() {
        final Person person = new Person(null, "First Last", 22);
        Person s = client.add(person);
        assertThat(s.id(), is(3)); //because we already have two people in our "database"
    }

    @Test
    void testFindByIdDeclarative() {
        Optional<Person> optionalPerson = client.findById(1);
        assertThat(optionalPerson.isPresent(), is(true));
    }

    @Test
    void testFindAllStreamDeclarative() {
        List<Person> list = client.findAll();
        assertThat(list, hasSize(2));
    }
}
