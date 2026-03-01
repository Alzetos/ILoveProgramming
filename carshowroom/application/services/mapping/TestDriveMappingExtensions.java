package ru.chekhet.carshowroom.application.services.mapping;

import ru.chekhet.carshowroom.application.contracts.cars.models.CarInstanceDto;
import ru.chekhet.carshowroom.application.contracts.testdrives.models.TestDriveDto;
import ru.chekhet.carshowroom.domain.testdrives.TestDriveRequest;

public class TestDriveMappingExtensions {
    public static TestDriveDto mapToDto(TestDriveRequest request, CarInstanceDto carDto) {
        return new TestDriveDto(
                request.getId().value(),
                request.getClientId().value(),
                carDto,
                request.getScheduledTime(),
                request.getState().getStatus().name()
        );
    }
}