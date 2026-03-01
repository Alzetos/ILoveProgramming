package ru.chekhet.carshowroom.application.abstractions.persistence.repositories;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.CarModelQuery;
import ru.chekhet.carshowroom.domain.cars.CarModel;

import java.util.List;

public interface CarModelRepository {
    CarModel add(CarModel model);
    void update(CarModel model);
    List<CarModel> query(CarModelQuery query);
}
