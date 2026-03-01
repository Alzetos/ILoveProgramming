package ru.chekhet.carshowroom.application.contracts.orders.models;

import ru.chekhet.carshowroom.application.contracts.cars.models.CarInstanceDto;

public record OrderDto(
        long id,
        long clientId,
        Long managerId,
        CarInstanceDto car,
        String status
) {}
