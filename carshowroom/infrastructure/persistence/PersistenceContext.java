package ru.chekhet.carshowroom.infrastructure.persistence;

import lombok.Getter;
import ru.chekhet.carshowroom.application.abstractions.persistence.IPersistenceContext;
import ru.chekhet.carshowroom.application.abstractions.persistence.repositories.*;

@Getter
public class PersistenceContext implements IPersistenceContext {
    private final UserRepository users = new InMemoryUserRepository();
    private final CarModelRepository carModels = new InMemoryCarModelRepository();
    private final CarComponentRepository carComponents = new InMemoryCarComponentRepository();
    private final CarInstanceRepository carInstances = new InMemoryCarInstanceRepository();
    private final OrderRepository orders = new InMemoryOrderRepository();
    private final TestDriveRepository testDrives = new InMemoryTestDriveRepository();
}