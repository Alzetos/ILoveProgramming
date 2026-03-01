package ru.chekhet.carshowroom.application.abstractions.persistence.queries;

import lombok.Builder;
import ru.chekhet.carshowroom.domain.cars.components.specs.BodyType;
import ru.chekhet.carshowroom.domain.cars.components.specs.DriveType;
import ru.chekhet.carshowroom.domain.cars.components.specs.FuelType;
import ru.chekhet.carshowroom.domain.cars.components.specs.TransmissionType;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.EngineCapacity;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.EnginePower;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarId;
import ru.chekhet.carshowroom.domain.cars.valueobjects.Color;

import java.util.List;

@Builder
public record CarInstanceQuery(
        List<CarId> ids,
        List<String> brands,
        List<String> modelNames,
        List<BodyType> bodyTypes,
        List<FuelType> fuelTypes,
        List<EnginePower> enginePowers,
        List<EngineCapacity> engineCapacities,
        List<TransmissionType> transmissionTypes,
        List<DriveType> driveTypes,
        List<Color> colors
) {
}