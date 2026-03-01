package ru.chekhet.carshowroom.domain.cars.components.valueobjects;

import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;

public record ComponentId(long value) {
    public ComponentId {
        if (value < 0) {
            throw new DomainValidationException("Component Id cannot be negative");
        }
    }
}
