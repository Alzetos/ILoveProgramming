package ru.chekhet.carshowroom.domain.testdrives.valueobjects;

import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;

public record TestDriveId(long value) {
    public TestDriveId {
        if (value < 0) {
            throw new DomainValidationException("TestDrive Id cannot be negative");
        }
    }
}