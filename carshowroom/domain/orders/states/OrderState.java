package ru.chekhet.carshowroom.domain.orders.states;

import ru.chekhet.carshowroom.domain.orders.states.valueobjects.OrderStatus;

public interface OrderState {
    public OrderStatus getStatus();

    boolean canApprove();
    boolean canRequirePayment();
    boolean canPay();
    boolean canSetReadyForPickup();
    boolean canWaitForDelivery();
    boolean canComplete();
    boolean canCancel();
}
