package ru.chekhet.carshowroom.domain.cars.components.valueobjects;

import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;

public record EnginePower(int value) {
    public EnginePower {
        if (value <= 0) {
            throw new DomainValidationException("Engine Power must be positive");
        }
    }
}
