package ru.chekhet.carshowroom.application.abstractions.persistence.repositories;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.OrderQuery;
import ru.chekhet.carshowroom.domain.orders.Order;

import java.util.List;

public interface OrderRepository {
    Order add(Order order);
    void update(Order order);
    List<Order> query(OrderQuery query);
}
