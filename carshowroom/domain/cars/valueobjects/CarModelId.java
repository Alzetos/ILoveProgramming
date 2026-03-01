package ru.chekhet.carshowroom.domain.cars.valueobjects;

import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;

public record CarModelId(long value) {
    public CarModelId {
        if (value < 0) throw new DomainValidationException("CarModel Id cannot be negative");
    }
}
