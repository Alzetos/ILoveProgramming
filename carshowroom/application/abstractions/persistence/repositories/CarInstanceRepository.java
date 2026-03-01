package ru.chekhet.carshowroom.application.abstractions.persistence.repositories;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.CarInstanceQuery;
import ru.chekhet.carshowroom.domain.cars.CarInstance;

import java.util.List;

public interface CarInstanceRepository {
    CarInstance add(CarInstance carInstance);
    void update(CarInstance carInstance);
    List<CarInstance> query(CarInstanceQuery query);
}
