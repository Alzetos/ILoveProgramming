package ru.chekhet.carshowroom.infrastructure.persistence;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.OrderQuery;
import ru.chekhet.carshowroom.application.abstractions.persistence.repositories.OrderRepository;
import ru.chekhet.carshowroom.domain.orders.Order;
import ru.chekhet.carshowroom.domain.orders.valueobjects.OrderId;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<OrderId, Order> storage = new ConcurrentHashMap<>();

    @Override
    public Order add(Order order) {
        storage.put(order.getId(), order);
        return order;
    }

    @Override
    public void update(Order order) {
        storage.put(order.getId(), order);
    }

    @Override
    public List<Order> query(OrderQuery query) { // change name OrderQuery query -> OrderQuery orderQuery and etc.
        return storage.values().stream()
                .filter(o -> query.orderIds() == null || query.orderIds().isEmpty() || query.orderIds().contains(o.getId()))
                .filter(o -> query.clientIds() == null || query.clientIds().isEmpty() || query.clientIds().contains(o.getClientId()))
                .filter(o -> query.managerIds() == null || query.managerIds().isEmpty() ||
                        (o.getManagerId() != null && query.managerIds().contains(o.getManagerId())))
                .filter(o -> query.statuses() == null || query.statuses().isEmpty() || query.statuses().contains(o.getState().getStatus()))
                .toList();
    }
}
