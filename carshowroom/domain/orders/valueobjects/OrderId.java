package ru.chekhet.carshowroom.domain.orders.valueobjects;

import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;

public record OrderId(long value) {
    public OrderId {
        if (value < 0) {
            throw new DomainValidationException("Order Id Cannot be negative");
        }
    }
}
