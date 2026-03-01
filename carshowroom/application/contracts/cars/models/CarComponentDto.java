package ru.chekhet.carshowroom.application.contracts.cars.models;

import java.math.BigDecimal;

public record CarComponentDto(
        long id,
        String category,
        String name,
        long extraPrice
) {}