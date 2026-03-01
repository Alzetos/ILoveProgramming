package ru.chekhet.carshowroom.domain.orders.states.valueobjects;

public enum OrderStatus {
    CREATED,
    MANAGER_APPROVED,
    WAREHOUSE_APPROVED,
    WAITING_FOR_PAYMENT,
    PAID,
    WAITING_FOR_DELIVERY,
    READY_FOR_PICKUP,
    COMPLETED,
    CANCELLED
}