package ru.chekhet.carshowroom.application.contracts.cars.models;

import java.util.List;

public record CarInstanceDto(
        long id,
        CarModelDto model,
        String colorHex,
        List<CarComponentDto> installedComponents,
        long totalPrice
) {}