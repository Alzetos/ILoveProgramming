package ru.chekhet.carshowroom.application.contracts.cars.models;

public record CarModelDto(
        long id,
        String brand,
        String modelName,
        long basePrice,

        double engineCapacity,
        int enginePower,

        String bodyType,
        String fuelType,
        String driveType,
        String transmissionType
) {}