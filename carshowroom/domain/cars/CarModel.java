package ru.chekhet.carshowroom.domain.cars;

import lombok.Builder;
import lombok.Value;
import lombok.NonNull;
import ru.chekhet.carshowroom.domain.cars.components.CarComponent;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.ComponentCategory;
import ru.chekhet.carshowroom.domain.cars.components.specs.*;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.*;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarModelId;
import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;
import ru.chekhet.carshowroom.domain.valueobjects.Money;

import java.util.Map;
import java.util.Set;

@Value
@Builder(toBuilder = true)
public class CarModel {
    @NonNull CarModelId id;
    @NonNull String brand;
    @NonNull String modelName;
    @NonNull Money basePrice;

    @NonNull EngineCapacity engineCapacity;
    @NonNull EnginePower enginePower;
    @NonNull BodyType bodyType;
    @NonNull FuelType fuelType;
    @NonNull DriveType driveType;
    @NonNull TransmissionType transmissionType;

    @Builder.Default
    Set<ComponentCategory> requiredCategories = Set.of();

    @Builder.Default
    Map<ComponentCategory, CarComponent> baseConfiguration = Map.of();

    public CarModel(CarModelId id, String brand, String modelName, Money basePrice,
                    EngineCapacity engineCapacity, EnginePower enginePower,
                    BodyType bodyType, FuelType fuelType, DriveType driveType,
                    TransmissionType transmissionType,
                    Set<ComponentCategory> requiredCategories,
                    Map<ComponentCategory, CarComponent> baseConfiguration) {
        this.id = id;
        this.brand = brand;
        this.modelName = modelName;
        this.basePrice = basePrice;
        this.engineCapacity = engineCapacity;
        this.enginePower = enginePower;
        this.bodyType = bodyType;
        this.fuelType = fuelType;
        this.driveType = driveType;
        this.transmissionType = transmissionType;
        this.requiredCategories = requiredCategories != null ? Set.copyOf(requiredCategories) : Set.of();
        this.baseConfiguration = baseConfiguration != null ? Map.copyOf(baseConfiguration) : Map.of();

        validate();
    }

    private void validate() {
        if (!baseConfiguration.keySet().containsAll(requiredCategories)) {
            throw new DomainValidationException("Car model " + modelName + " does not have required nodes");
        }
    }

    public boolean isCategoryRequired(ComponentCategory category) {
        return requiredCategories.contains(category);
    }

    public CarComponent getBaseComponentFor(ComponentCategory category) {
        return baseConfiguration.get(category);
    }

    public Set<ComponentCategory> getAvailableCategories() {
        return baseConfiguration.keySet();
    }
}