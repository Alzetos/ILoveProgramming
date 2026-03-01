package ru.chekhet.carshowroom.application.contracts.cars.operations;

import ru.chekhet.carshowroom.application.contracts.cars.models.CarComponentDto;
import java.util.List;

public final class CreateCarComponent {
    private CreateCarComponent() {}

    public record Request(
            String name,
            String category,
            long extraPrice,
            List<Long> compatibleModelIds
    ) {}

    public sealed interface Response {
        record Success(CarComponentDto component) implements Response {}
        record Failure(String message) implements Response {}
    }
}