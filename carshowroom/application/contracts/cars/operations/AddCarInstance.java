package ru.chekhet.carshowroom.application.contracts.cars.operations;

import ru.chekhet.carshowroom.application.contracts.cars.models.CarInstanceDto;
import java.util.List;

public final class AddCarInstance {
    private AddCarInstance() {}

    public record Request(
            long carModelId,
            String colorHex,
            List<Long> installedComponentIds
    ) {}

    public sealed interface Response {
        record Success(CarInstanceDto carInstance) implements Response {}
        record Failure(String message) implements Response {}
    }
}