package ru.chekhet.carshowroom.domain.cars;

import lombok.Builder;
import lombok.Value;
import ru.chekhet.carshowroom.domain.cars.components.CarComponent;
import ru.chekhet.carshowroom.domain.cars.components.specs.BodyType;
import ru.chekhet.carshowroom.domain.cars.components.specs.DriveType;
import ru.chekhet.carshowroom.domain.cars.components.specs.FuelType;
import ru.chekhet.carshowroom.domain.cars.components.specs.TransmissionType;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.ComponentCategory;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.EngineCapacity;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.EnginePower;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarId;
import ru.chekhet.carshowroom.domain.cars.valueobjects.Color;
import ru.chekhet.carshowroom.domain.valueobjects.Money;

import java.util.Map;

@Value
@Builder
public class CarInstance {
    CarId id;
    CustomConfiguration configuration;
    Color color;

    public CarModel getModel() {
        return configuration.getModel();
    }

    public String getBrand() {
        return getModel().getBrand();
    }

    public String getModelName() {
        return getModel().getModelName();
    }

    public EnginePower getEnginePower() {
        return getModel().getEnginePower();
    }

    public EngineCapacity getEngineCapacity() {
        return getModel().getEngineCapacity();
    }

    public TransmissionType getTransmissionType() {
        return getModel().getTransmissionType();
    }

    public BodyType getBodyType() {
        return getModel().getBodyType();
    }

    public FuelType getFuelType() {
        return getModel().getFuelType();
    }

    public DriveType getDriveType() {
        return getModel().getDriveType();
    }

    public Map<ComponentCategory, CarComponent> getInstalledComponents() {
        return configuration.getSelectedComponents();
    }

    public Money getPrice() {
        return configuration.calculateTotalPrice();
    }
}