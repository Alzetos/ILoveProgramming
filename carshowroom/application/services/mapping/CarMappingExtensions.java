package ru.chekhet.carshowroom.application.services.mapping;

import ru.chekhet.carshowroom.application.contracts.cars.models.*;
import ru.chekhet.carshowroom.domain.cars.*;
import ru.chekhet.carshowroom.domain.cars.components.CarComponent;

import java.util.stream.Collectors;

public class CarMappingExtensions {

    public static CarModelDto mapToDto(CarModel model) {
        return new CarModelDto(
                model.getId().value(),
                model.getBrand(),
                model.getModelName(),
                model.getBasePrice().value(),
                model.getEngineCapacity().value(),
                model.getEnginePower().value(),
                model.getBodyType().name(),
                model.getFuelType().name(),
                model.getDriveType().name(),
                model.getTransmissionType().name()
        );
    }

    public static CarComponentDto mapToDto(CarComponent component) {
        return new CarComponentDto(
                component.id().value(),
                component.category().value(),
                component.name(),
                component.extraPrice().value()
        );
    }

    public static CarInstanceDto mapToDto(CarInstance instance) {
        return new CarInstanceDto(
                instance.getId().value(),
                mapToDto(instance.getModel()),
                instance.getColor().hexCode(),
                instance.getInstalledComponents().values().stream()
                        .map(CarMappingExtensions::mapToDto)
                        .collect(Collectors.toList()),
                instance.getPrice().value()
        );
    }
}