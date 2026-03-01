package ru.chekhet.carshowroom.application.abstractions.persistence.queries;

import lombok.Builder;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.ComponentCategory;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.ComponentId;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarModelId;

import java.util.List;

@Builder
public record CarComponentQuery(
        List<ComponentId> ids,
        List<ComponentCategory> categories,
        List<CarModelId> compatibleWithModelIds
) {}