package ru.chekhet.carshowroom.domain.valueobjects;

public record Money(long value) {
    public Money {
        if (value < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
    }

    public Money add(Money money) {
        return new Money(value + money.value());
    }
}
