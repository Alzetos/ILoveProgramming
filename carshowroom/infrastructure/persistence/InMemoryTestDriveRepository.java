package ru.chekhet.carshowroom.infrastructure.persistence;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.TestDriveQuery;
import ru.chekhet.carshowroom.application.abstractions.persistence.repositories.TestDriveRepository;
import ru.chekhet.carshowroom.domain.testdrives.TestDriveRequest;
import ru.chekhet.carshowroom.domain.testdrives.valueobjects.TestDriveId;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTestDriveRepository implements TestDriveRepository {
    private final Map<TestDriveId, TestDriveRequest> storage = new ConcurrentHashMap<>();

    @Override
    public TestDriveRequest add(TestDriveRequest testDrive) {
        storage.put(testDrive.getId(), testDrive);
        return testDrive;
    }

    @Override
    public void update(TestDriveRequest testDrive) {
        storage.put(testDrive.getId(), testDrive);
    }

    @Override
    public List<TestDriveRequest> query(TestDriveQuery query) {
        return storage.values().stream()
                .filter(t -> query.ids() == null || query.ids().isEmpty() || query.ids().contains(t.getId()))
                .filter(t -> query.clientIds() == null || query.clientIds().isEmpty() || query.clientIds().contains(t.getClientId()))
                .filter(t -> query.carIds() == null || query.carIds().isEmpty() || query.carIds().contains(t.getCarId()))
                .toList();
    }
}