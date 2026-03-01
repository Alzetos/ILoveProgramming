package ru.chekhet.carshowroom.application.contracts.cars;

import ru.chekhet.carshowroom.application.contracts.cars.operations.*;

public interface ICarService {
    GetCatalog.Response getCatalog(GetCatalog.Request request);
    GetCarDetails.Response getDetails(GetCarDetails.Request request);

    CreateCarModel.Response createModel(CreateCarModel.Request request);
    CreateCarComponent.Response createComponent(CreateCarComponent.Request request);
    AddCarInstance.Response addInstance(AddCarInstance.Request request);
}