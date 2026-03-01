package ru.chekhet.carshowroom.application.contracts.orders;

import ru.chekhet.carshowroom.application.contracts.orders.operations.*;

public interface IOrderService {

    CreateInStockOrder.Response createInStock(CreateInStockOrder.Request request);
    CreateCustomOrder.Response createCustom(CreateCustomOrder.Request request);


    ApproveByManager.Response approveByManager(ApproveByManager.Request request);
    ApproveByWarehouse.Response approveByWarehouse(ApproveByWarehouse.Request request);
    PayOrder.Response pay(PayOrder.Request request);
    StartDelivery.Response startDelivery(StartDelivery.Request request);
    SetReadyForPickup.Response setReadyForPickup(SetReadyForPickup.Request request);
    CompleteOrder.Response complete(CompleteOrder.Request request);
    CancelOrder.Response cancel(CancelOrder.Request request);
}