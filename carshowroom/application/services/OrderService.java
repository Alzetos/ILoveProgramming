package ru.chekhet.carshowroom.application.services;

import lombok.RequiredArgsConstructor;
import ru.chekhet.carshowroom.application.abstractions.persistence.IPersistenceContext;
import ru.chekhet.carshowroom.application.abstractions.persistence.queries.*;
import ru.chekhet.carshowroom.application.contracts.orders.IOrderService;
import ru.chekhet.carshowroom.application.contracts.orders.operations.*;
import ru.chekhet.carshowroom.application.services.mapping.OrderMappingExtensions;
import ru.chekhet.carshowroom.domain.carconfigurators.CarConfigurator;
import ru.chekhet.carshowroom.domain.cars.*;
import ru.chekhet.carshowroom.domain.cars.components.CarComponent;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.ComponentId;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarId;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarModelId;
import ru.chekhet.carshowroom.domain.cars.valueobjects.Color;
import ru.chekhet.carshowroom.domain.orders.*;
import ru.chekhet.carshowroom.domain.orders.valueobjects.OrderId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserRole;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IPersistenceContext persistence;

    @Override
    public CreateInStockOrder.Response createInStock(CreateInStockOrder.Request request) {
        try {
            CarInstance car = persistence.getCarInstances()
                    .query(CarInstanceQuery.builder().ids(List.of(new CarId(request.carId()))).build())
                    .stream().findFirst()
                    .orElse(null);

            if (car == null) {
                return new CreateInStockOrder.Response.Failure("Car not found in stock.");
            }

            InStockCarOrder order = new InStockCarOrder(
                    new OrderId(System.currentTimeMillis()),
                    new UserId(request.clientId()),
                    car
            );

            autoAssignManager(order);
            persistence.getOrders().add(order);

            return new CreateInStockOrder.Response.Success(OrderMappingExtensions.mapToDto(order));
        } catch (Exception e) {
            return new CreateInStockOrder.Response.Failure(e.getMessage());
        }
    }

    @Override
    public CreateCustomOrder.Response createCustom(CreateCustomOrder.Request request) {
        try {
            CarModel model = persistence.getCarModels()
                    .query(CarModelQuery.builder().ids(List.of(new CarModelId(request.carModelId()))).build())
                    .stream().findFirst()
                    .orElse(null);

            if (model == null) {
                return new CreateCustomOrder.Response.Failure("Car model not found.");
            }

            CarConfigurator configurator = new CarConfigurator(model);

            for (Map.Entry<String, Long> selection : request.selectedComponents().entrySet()) {
                CarComponent component = persistence.getCarComponents()
                        .query(CarComponentQuery.builder().ids(List.of(new ComponentId(selection.getValue()))).build())
                        .stream().findFirst()
                        .orElse(null);

                if (component == null) {
                    return new CreateCustomOrder.Response.Failure("Component " + selection.getKey() + " not found.");
                }
                configurator.selectComponent(component);
            }

            CarInstance customCar = CarInstance.builder()
                    .id(new CarId(0))
                    .configuration(configurator.build())
                    .color(new Color(request.colorHex()))
                    .build();

            CustomCarOrder order = new CustomCarOrder(
                    new OrderId(System.currentTimeMillis()),
                    new UserId(request.clientId()),
                    customCar
            );

            autoAssignManager(order);
            persistence.getOrders().add(order);

            return new CreateCustomOrder.Response.Success(OrderMappingExtensions.mapToDto(order));
        } catch (Exception e) {
            return new CreateCustomOrder.Response.Failure(e.getMessage());
        }
    }

    @Override
    public ApproveByManager.Response approveByManager(ApproveByManager.Request request) {
        try {
            Order order = findOrder(request.orderId());
            if (order == null) return new ApproveByManager.Response.Failure("Order not found.");

            if (!(order instanceof InStockCarOrder inStock)) {
                return new ApproveByManager.Response.Failure("Action allowed only for in-stock orders.");
            }

            inStock.approveByManager();
            inStock.requirePayment();

            persistence.getOrders().update(inStock);
            return new ApproveByManager.Response.Success(OrderMappingExtensions.mapToDto(inStock));
        } catch (Exception e) {
            return new ApproveByManager.Response.Failure(e.getMessage());
        }
    }

    @Override
    public ApproveByWarehouse.Response approveByWarehouse(ApproveByWarehouse.Request request) {
        try {
            Order order = findOrder(request.orderId());
            if (order == null) return new ApproveByWarehouse.Response.Failure("Order not found.");

            if (!(order instanceof CustomCarOrder custom)) {
                return new ApproveByWarehouse.Response.Failure("Action allowed only for custom orders.");
            }

            custom.approveByWarehouse();
            custom.requirePayment();

            persistence.getOrders().update(custom);
            return new ApproveByWarehouse.Response.Success(OrderMappingExtensions.mapToDto(custom));
        } catch (Exception e) {
            return new ApproveByWarehouse.Response.Failure(e.getMessage());
        }
    }

    @Override
    public PayOrder.Response pay(PayOrder.Request request) {
        try {
            Order order = findOrder(request.orderId());
            if (order == null) return new PayOrder.Response.Failure("Order not found.");

            order.pay();

            persistence.getOrders().update(order);
            return new PayOrder.Response.Success(OrderMappingExtensions.mapToDto(order));
        } catch (Exception e) {
            return new PayOrder.Response.Failure(e.getMessage());
        }
    }

    @Override
    public StartDelivery.Response startDelivery(StartDelivery.Request request) {
        try {
            Order order = findOrder(request.orderId());
            if (order == null) return new StartDelivery.Response.Failure("Order not found.");

            if (!(order instanceof CustomCarOrder custom)) {
                return new StartDelivery.Response.Failure("Delivery stage is only applicable for custom orders.");
            }

            custom.waitForDelivery();

            persistence.getOrders().update(custom);
            return new StartDelivery.Response.Success(OrderMappingExtensions.mapToDto(custom));
        } catch (Exception e) {
            return new StartDelivery.Response.Failure(e.getMessage());
        }
    }

    @Override
    public SetReadyForPickup.Response setReadyForPickup(SetReadyForPickup.Request request) {
        try {
            Order order = findOrder(request.orderId());
            if (order == null) return new SetReadyForPickup.Response.Failure("Order not found.");

            order.setReadyForPickup();

            persistence.getOrders().update(order);
            return new SetReadyForPickup.Response.Success(OrderMappingExtensions.mapToDto(order));
        } catch (Exception e) {
            return new SetReadyForPickup.Response.Failure(e.getMessage());
        }
    }

    @Override
    public CompleteOrder.Response complete(CompleteOrder.Request request) {
        try {
            Order order = findOrder(request.orderId());
            if (order == null) return new CompleteOrder.Response.Failure("Order not found.");

            order.complete();

            persistence.getOrders().update(order);
            return new CompleteOrder.Response.Success(OrderMappingExtensions.mapToDto(order));
        } catch (Exception e) {
            return new CompleteOrder.Response.Failure(e.getMessage());
        }
    }

    @Override
    public CancelOrder.Response cancel(CancelOrder.Request request) {
        try {
            Order order = findOrder(request.orderId());
            if (order == null) return new CancelOrder.Response.Failure("Order not found.");

            order.cancel();

            persistence.getOrders().update(order);
            return new CancelOrder.Response.Success(OrderMappingExtensions.mapToDto(order));
        } catch (Exception e) {
            return new CancelOrder.Response.Failure(e.getMessage());
        }
    }

    private Order findOrder(long id) {
        return persistence.getOrders()
                .query(OrderQuery.builder().orderIds(List.of(new OrderId(id))).build())
                .stream().findFirst()
                .orElse(null);
    }

    private void autoAssignManager(Order order) {
        persistence.getUsers()
                .query(UserQuery.builder().roles(List.of(UserRole.SALES_MANAGER)).build())
                .stream()
                .findAny()
                .ifPresent(m -> order.assignManager(m.getId()));
    }
}