package ru.chekhet.carshowroom.application.abstractions.persistence.queries;

import lombok.Builder;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarModelId;

import java.util.List;

@Builder
public record CarModelQuery(
        List<CarModelId> ids,
        List<String> brands,
        List<String> modelNames
) {}
