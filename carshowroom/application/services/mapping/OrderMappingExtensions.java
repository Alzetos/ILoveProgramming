package ru.chekhet.carshowroom.application.services.mapping;

import ru.chekhet.carshowroom.application.contracts.orders.models.OrderDto;
import ru.chekhet.carshowroom.domain.orders.Order;

public class OrderMappingExtensions {
    public static OrderDto mapToDto(Order order) {
        return new OrderDto(
                order.getId().value(),
                order.getClientId().value(),
                order.getManagerId() != null ? order.getManagerId().value() : null,
                CarMappingExtensions.mapToDto(order.getCarInstance()),
                order.getState().getStatus().name()
        );
    }
}