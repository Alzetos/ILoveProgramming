package ru.chekhet.carshowroom.domain.cars.valueobjects;

import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;

public record CarId(long value) {
    public CarId {
        if (value < 0) {
            throw new DomainValidationException("Car Id cannot be negative");
        }
    }
}
