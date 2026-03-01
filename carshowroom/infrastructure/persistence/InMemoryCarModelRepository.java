package ru.chekhet.carshowroom.infrastructure.persistence;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.CarModelQuery;
import ru.chekhet.carshowroom.application.abstractions.persistence.repositories.CarModelRepository;
import ru.chekhet.carshowroom.domain.cars.CarModel;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarModelId;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCarModelRepository implements CarModelRepository {
    private final Map<CarModelId, CarModel> storage = new ConcurrentHashMap<>();

    @Override
    public CarModel add(CarModel model) {
        storage.put(model.getId(), model);
        return model;
    }

    @Override
    public void update(CarModel model) {
        storage.put(model.getId(), model);
    }

    @Override
    public List<CarModel> query(CarModelQuery query) {
        return storage.values().stream()
                .filter(m -> query.ids() == null || query.ids().isEmpty() || query.ids().contains(m.getId()))
                .filter(m -> query.brands() == null || query.brands().isEmpty() || query.brands().contains(m.getBrand()))
                .filter(m -> query.modelNames() == null || query.modelNames().isEmpty() || query.modelNames().contains(m.getModelName()))
                .toList();
    }
}