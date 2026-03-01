package ru.chekhet.carshowroom.application.contracts.orders.operations;

import ru.chekhet.carshowroom.application.contracts.orders.models.OrderDto;

public final class CreateInStockOrder {
    private CreateInStockOrder() {}

    public record Request(
            long clientId,
            long carId
    ) {}

    public sealed interface Response {
        record Success(OrderDto order) implements Response {}
        record Failure(String message) implements Response {}
    }
}