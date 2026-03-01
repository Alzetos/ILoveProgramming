package ru.chekhet.carshowroom.application.contracts.orders.operations;

import ru.chekhet.carshowroom.application.contracts.orders.models.OrderDto;

public final class CompleteOrder {
    private CompleteOrder() {}

    public record Request(long orderId) {}

    public sealed interface Response {
        record Success(OrderDto order) implements Response {}
        record Failure(String message) implements Response {}
    }
}