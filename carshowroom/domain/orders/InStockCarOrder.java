package ru.chekhet.carshowroom.domain.orders;

import lombok.Getter;
import ru.chekhet.carshowroom.domain.cars.CarInstance;
import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;
import ru.chekhet.carshowroom.domain.orders.states.ManagerApprovedOrderState;
import ru.chekhet.carshowroom.domain.orders.valueobjects.OrderId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;

@Getter
public class InStockCarOrder extends Order {

    public InStockCarOrder(OrderId id, UserId clientId, CarInstance carInstance) {
        super(id, clientId, carInstance);
    }


    public void approveByManager() {
        if (!state.canApprove()) {
            throw new DomainValidationException("Cannot approve by manager in state: " + state.getStatus());
        }
        this.state = new ManagerApprovedOrderState();
    }
}
