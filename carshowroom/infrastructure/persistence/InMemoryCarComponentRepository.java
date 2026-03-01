package ru.chekhet.carshowroom.infrastructure.persistence;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.CarComponentQuery;
import ru.chekhet.carshowroom.application.abstractions.persistence.repositories.CarComponentRepository;
import ru.chekhet.carshowroom.domain.cars.components.CarComponent;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.ComponentId;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCarComponentRepository implements CarComponentRepository {
    private final Map<ComponentId, CarComponent> storage = new ConcurrentHashMap<>();

    @Override
    public CarComponent add(CarComponent component) {
        storage.put(component.id(), component);
        return component;
    }

    @Override
    public void update(CarComponent component) {
        storage.put(component.id(), component);
    }

    @Override
    public List<CarComponent> query(CarComponentQuery query) {
        return storage.values().stream()
                .filter(c -> query.ids() == null || query.ids().isEmpty() || query.ids().contains(c.id()))
                .filter(c -> query.categories() == null || query.categories().isEmpty() || query.categories().contains(c.category()))
                .filter(c -> query.compatibleWithModelIds() == null || query.compatibleWithModelIds().isEmpty() ||
                        query.compatibleWithModelIds().stream().anyMatch(c::isCompatibleWith))
                .toList();
    }
}