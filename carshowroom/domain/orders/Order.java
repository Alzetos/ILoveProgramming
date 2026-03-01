package ru.chekhet.carshowroom.domain.orders;

import lombok.Getter;
import ru.chekhet.carshowroom.domain.cars.CarInstance;
import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;
import ru.chekhet.carshowroom.domain.orders.states.*;
import ru.chekhet.carshowroom.domain.orders.valueobjects.OrderId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;
import ru.chekhet.carshowroom.domain.valueobjects.Money;

@Getter
public abstract class Order {
    private final OrderId id;
    private final UserId clientId;
    private UserId managerId;
    private final CarInstance carInstance;
    protected OrderState state;

    protected Order(OrderId id, UserId clientId, CarInstance carInstance) {
        this.id = id;
        this.clientId = clientId;
        this.carInstance = carInstance;
        this.state = new CreatedOrderState();
    }

    public void assignManager(UserId managerId) {
        if (this.managerId != null) {
            throw new DomainValidationException("Manager is already assigned to order " + id.value());
        }
        this.managerId = managerId;
    }

    public Money calculateTotal() {
        return carInstance.getPrice();
    }

    public void requirePayment() {
        if (!state.canRequirePayment()) {
            throw new DomainValidationException("Cannot require payment in state: " + state.getStatus());
        }
        this.state = new WaitingForPaymentOrderState();
    }

    public void pay() {
        if (!state.canPay()) {
            throw new DomainValidationException("Cannot pay in state: " + state.getStatus());
        }
        this.state = new PaidOrderState();
    }

    public void setReadyForPickup() {
        if (!state.canSetReadyForPickup()) {
            throw new DomainValidationException("Cannot set ready for pickup in state: " + state.getStatus());
        }
        this.state = new ReadyForPickupOrderState();
    }

    public void complete() {
        if (!state.canComplete()) {
            throw new DomainValidationException("Cannot complete order in state: " + state.getStatus());
        }
        this.state = new CompletedOrderState();
    }

    public void cancel() {
        if (!state.canCancel()) {
            throw new DomainValidationException("Cannot cancel order in state: " + state.getStatus());
        }
        this.state = new CancelledOrderState();
    }
}
