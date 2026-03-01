package ru.chekhet.carshowroom.application.contracts.cars.operations;

import ru.chekhet.carshowroom.application.contracts.cars.models.CarModelDto;
import java.util.List;
import java.util.Map;

public final class CreateCarModel {
    private CreateCarModel() {}

    public record Request(
            String brand,
            String modelName,
            long basePrice,

            double engineCapacity,
            int enginePower,
            String bodyType,
            String fuelType,
            String driveType,
            String transmissionType,
            List<String> requiredCategories,
            Map<String, Long> baseComponentIds
    ) {}

    public sealed interface Response {
        record Success(CarModelDto carModel) implements Response {}
        record Failure(String message) implements Response {}
    }
}