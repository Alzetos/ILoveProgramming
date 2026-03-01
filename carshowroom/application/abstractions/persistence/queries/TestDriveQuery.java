package ru.chekhet.carshowroom.application.abstractions.persistence.queries;

import lombok.Builder;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarId;
import ru.chekhet.carshowroom.domain.testdrives.valueobjects.TestDriveId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;

import java.util.List;

@Builder
public record TestDriveQuery(
        List<TestDriveId> ids,
        List<UserId> clientIds,
        List<CarId> carIds
) {}
