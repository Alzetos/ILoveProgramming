package ru.chekhet.carshowroom.domain.orders;

import lombok.Getter;
import ru.chekhet.carshowroom.domain.cars.CarInstance;
import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;
import ru.chekhet.carshowroom.domain.orders.states.WaitingForDeliveryOrderState;
import ru.chekhet.carshowroom.domain.orders.states.WarehouseApprovedOrderState;
import ru.chekhet.carshowroom.domain.orders.valueobjects.OrderId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;

@Getter
public class CustomCarOrder extends Order {

    public CustomCarOrder(OrderId id, UserId clientId, CarInstance carInstance) {
        super(id, clientId, carInstance);
    }

    public void approveByWarehouse() {
        if (!state.canApprove()) {
            throw new DomainValidationException("Cannot approve by warehouse in state: " + state.getStatus());
        }
        this.state = new WarehouseApprovedOrderState();
    }

    public void waitForDelivery() {
        if (!state.canWaitForDelivery()) {
            throw new DomainValidationException("Cannot set waiting for delivery in state: " + state.getStatus());
        }
        this.state = new WaitingForDeliveryOrderState();
    }
}
