package ru.chekhet.carshowroom.domain.cars;

import lombok.Builder;
import lombok.Value;
import ru.chekhet.carshowroom.domain.cars.components.CarComponent;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.ComponentCategory;
import ru.chekhet.carshowroom.domain.valueobjects.Money;

import java.util.Map;

@Value
@Builder
public class CustomConfiguration {
    CarModel model;
    Map<ComponentCategory, CarComponent> selectedComponents;

    public Money calculateTotalPrice() {
        return selectedComponents.values().stream()
                .map(CarComponent::extraPrice)
                .reduce(model.getBasePrice(), Money::add);
    }
}