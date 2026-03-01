package ru.chekhet.carshowroom.application.contracts.cars.operations;

import ru.chekhet.carshowroom.application.contracts.cars.models.CarInstanceDto;

public final class GetCarDetails {
    private GetCarDetails() {}

    public record Request(long carId) {}

    public sealed interface Response {
        record Success(CarInstanceDto car) implements Response {}
        record Failure(String message) implements Response {}
    }
}