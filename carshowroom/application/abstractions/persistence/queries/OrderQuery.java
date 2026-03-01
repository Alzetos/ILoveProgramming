package ru.chekhet.carshowroom.application.abstractions.persistence.queries;

import lombok.Builder;
import ru.chekhet.carshowroom.domain.orders.states.valueobjects.OrderStatus;
import ru.chekhet.carshowroom.domain.orders.valueobjects.OrderId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;

import java.util.List;

@Builder
public record OrderQuery(
        List<OrderId> orderIds,
        List<UserId> clientIds,
        List<UserId> managerIds,
        List<OrderStatus> statuses
) {
}
