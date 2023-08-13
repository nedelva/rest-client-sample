package org.logiware.rest.client.example.domain;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Person(Integer id, String name, Integer age) {
}
