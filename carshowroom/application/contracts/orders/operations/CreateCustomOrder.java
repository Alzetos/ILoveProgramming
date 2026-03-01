package ru.chekhet.carshowroom.application.contracts.orders.operations;

import ru.chekhet.carshowroom.application.contracts.orders.models.OrderDto;

import java.util.Map;

public final class CreateCustomOrder {
    private CreateCustomOrder() {}

    public record Request(
            long clientId,
            long carModelId,
            String colorHex,
            Map<String, Long> selectedComponents
    ) {}

    public sealed interface Response {
        record Success(OrderDto order) implements Response {}
        record Failure(String message) implements Response {}
    }
}