package ru.chekhet.carshowroom.domain.cars.components.valueobjects;

import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;

public record EngineCapacity(double value) {
    public EngineCapacity {
        if (value <= 0) {
            throw new DomainValidationException("Engine Capacity must be positive");
        }
    }
}
