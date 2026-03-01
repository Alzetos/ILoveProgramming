package ru.chekhet.carshowroom.domain.orders.states;

import ru.chekhet.carshowroom.domain.orders.states.valueobjects.OrderStatus;

public class CancelledOrderState implements OrderState {
    @Override
    public OrderStatus getStatus() {
        return OrderStatus.CANCELLED;
    }

    @Override
    public boolean canApprove() {
        return false;
    }

    @Override
    public boolean canRequirePayment() {
        return false;
    }

    @Override
    public boolean canPay() {
        return false;
    }

    @Override
    public boolean canSetReadyForPickup() {
        return false;
    }

    @Override
    public boolean canWaitForDelivery() {
        return false;
    }

    @Override
    public boolean canComplete() {
        return false;
    }

    @Override
    public boolean canCancel() {
        return false;
    }
}
