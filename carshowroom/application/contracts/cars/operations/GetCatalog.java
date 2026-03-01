package ru.chekhet.carshowroom.application.contracts.cars.operations;

import ru.chekhet.carshowroom.application.contracts.cars.models.CarInstanceDto;

import java.util.List;

public final class GetCatalog {
    private GetCatalog() {}

    public record Request(
            String brand,
            String modelName,
            Long minPrice,
            Long maxPrice,
            String bodyType,
            String fuelType,
            String transmissionType,
            String driveType,
            Integer minPower,
            Integer maxPower,
            Double minCapacity,
            Double maxCapacity,
            String colorHex
    ) {}

    public record Response(List<CarInstanceDto> cars) {}
}