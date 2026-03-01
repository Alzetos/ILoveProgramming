package ru.chekhet.carshowroom.application.abstractions.persistence.repositories;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.TestDriveQuery;
import ru.chekhet.carshowroom.domain.testdrives.TestDriveRequest;

import java.util.List;

public interface TestDriveRepository {
    TestDriveRequest add(TestDriveRequest testDrive);
    void update(TestDriveRequest testDrive);
    List<TestDriveRequest> query(TestDriveQuery query);
}
