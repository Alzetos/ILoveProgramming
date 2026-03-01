package ru.chekhet.carshowroom.domain.users.valueobjects;

import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;

public record UserId(long value) {
    public UserId {
        if (value < 0) {
            throw new DomainValidationException("User ID cannot be negative");
        }
    }
}
