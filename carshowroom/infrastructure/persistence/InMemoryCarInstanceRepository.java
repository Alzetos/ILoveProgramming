package ru.chekhet.carshowroom.infrastructure.persistence;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.CarInstanceQuery;
import ru.chekhet.carshowroom.application.abstractions.persistence.repositories.CarInstanceRepository;
import ru.chekhet.carshowroom.domain.cars.CarInstance;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarId;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCarInstanceRepository implements CarInstanceRepository {
    private final Map<CarId, CarInstance> storage = new ConcurrentHashMap<>();

    @Override
    public CarInstance add(CarInstance carInstance) {
        storage.put(carInstance.getId(), carInstance);
        return carInstance;
    }

    @Override
    public void update(CarInstance carInstance) {
        storage.put(carInstance.getId(), carInstance);
    }

    @Override
    public List<CarInstance> query(CarInstanceQuery query) {
        return storage.values().stream()
                .filter(c -> query.ids() == null || query.ids().isEmpty() || query.ids().contains(c.getId()))
                .filter(c -> query.brands() == null || query.brands().isEmpty() || query.brands().contains(c.getBrand()))
                .filter(c -> query.modelNames() == null || query.modelNames().isEmpty() || query.modelNames().contains(c.getModelName()))
                .filter(c -> query.bodyTypes() == null || query.bodyTypes().isEmpty() || query.bodyTypes().contains(c.getBodyType()))
                .filter(c -> query.fuelTypes() == null || query.fuelTypes().isEmpty() || query.fuelTypes().contains(c.getFuelType()))
                .filter(c -> query.transmissionTypes() == null || query.transmissionTypes().isEmpty() || query.transmissionTypes().contains(c.getTransmissionType()))
                .filter(c -> query.driveTypes() == null || query.driveTypes().isEmpty() || query.driveTypes().contains(c.getDriveType()))
                .filter(c -> query.colors() == null || query.colors().isEmpty() || query.colors().contains(c.getColor()))
                .filter(c -> query.enginePowers() == null || query.enginePowers().isEmpty() ||
                        query.enginePowers().stream().anyMatch(p -> c.getEnginePower().value() >= p.value()))
                .filter(c -> query.engineCapacities() == null || query.engineCapacities().isEmpty() ||
                        query.engineCapacities().stream().anyMatch(cap -> c.getEngineCapacity().value() >= cap.value()))
                .toList();
    }
}