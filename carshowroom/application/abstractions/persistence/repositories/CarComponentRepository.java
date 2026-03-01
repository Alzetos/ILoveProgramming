package ru.chekhet.carshowroom.application.abstractions.persistence.repositories;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.CarComponentQuery;
import ru.chekhet.carshowroom.domain.cars.components.CarComponent;

import java.util.List;

public interface CarComponentRepository {
    CarComponent add(CarComponent component);
    void update(CarComponent component);
    List<CarComponent> query(CarComponentQuery query);
}
