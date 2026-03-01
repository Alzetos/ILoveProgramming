package ru.chekhet.carshowroom.application.contracts.orders.operations;

import ru.chekhet.carshowroom.application.contracts.orders.models.OrderDto;

public final class ApproveByManager {
    private ApproveByManager() {}

    public record Request(long orderId, long managerId) {}

    public sealed interface Response {
        record Success(OrderDto order) implements Response {}
        record Failure(String message) implements Response {}
    }
}