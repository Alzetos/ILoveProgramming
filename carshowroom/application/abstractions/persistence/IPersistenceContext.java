package ru.chekhet.carshowroom.application.abstractions.persistence;

import ru.chekhet.carshowroom.application.abstractions.persistence.repositories.*;

public interface IPersistenceContext {
    UserRepository getUsers();

    CarModelRepository getCarModels();

    CarComponentRepository getCarComponents();

    CarInstanceRepository getCarInstances();

    OrderRepository getOrders();

    TestDriveRepository getTestDrives();
}
