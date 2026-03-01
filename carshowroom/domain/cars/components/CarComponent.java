package ru.chekhet.carshowroom.domain.cars.components;

import ru.chekhet.carshowroom.domain.cars.components.valueobjects.ComponentCategory;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.ComponentId;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarModelId;
import ru.chekhet.carshowroom.domain.valueobjects.Money;

import java.util.Set;

public record CarComponent(
        ComponentId id,
        ComponentCategory category,
        String name,
        Money extraPrice,
        Set<CarModelId> compatibleModelIds
) {
    public boolean isCompatibleWith(CarModelId modelId) {
        return compatibleModelIds.contains(modelId);
    }
}
