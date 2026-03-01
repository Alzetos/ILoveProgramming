package ru.chekhet.carshowroom.application.services;

import lombok.RequiredArgsConstructor;
import ru.chekhet.carshowroom.application.abstractions.persistence.IPersistenceContext;
import ru.chekhet.carshowroom.application.abstractions.persistence.queries.CarInstanceQuery;
import ru.chekhet.carshowroom.application.abstractions.persistence.queries.TestDriveQuery;
import ru.chekhet.carshowroom.application.contracts.cars.models.CarInstanceDto;
import ru.chekhet.carshowroom.application.contracts.testdrives.ITestDriveService;
import ru.chekhet.carshowroom.application.contracts.testdrives.models.TestDriveDto;
import ru.chekhet.carshowroom.application.contracts.testdrives.operations.*;
import ru.chekhet.carshowroom.application.services.mapping.CarMappingExtensions;
import ru.chekhet.carshowroom.application.services.mapping.TestDriveMappingExtensions;
import ru.chekhet.carshowroom.domain.cars.CarInstance;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarId;
import ru.chekhet.carshowroom.domain.testdrives.TestDriveRequest;
import ru.chekhet.carshowroom.domain.testdrives.valueobjects.TestDriveId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;

import java.util.List;

@RequiredArgsConstructor
public class TestDriveService implements ITestDriveService {

    private final IPersistenceContext persistence;

    @Override
    public CreateTestDrive.Response create(CreateTestDrive.Request request) {
        try {
            CarInstance car = persistence.getCarInstances()
                    .query(CarInstanceQuery.builder().ids(List.of(new CarId(request.carId()))).build())
                    .stream().findFirst().orElse(null);

            if (car == null) return new CreateTestDrive.Response.Failure("Car not found.");

            TestDriveRequest testDrive = new TestDriveRequest(
                    new TestDriveId(System.currentTimeMillis()),
                    new UserId(request.clientId()),
                    car.getId(),
                    request.scheduledTime()
            );

            persistence.getTestDrives().add(testDrive);

            CarInstanceDto carDto = CarMappingExtensions.mapToDto(car);
            return new CreateTestDrive.Response.Success(TestDriveMappingExtensions.mapToDto(testDrive, carDto));
        } catch (Exception e) {
            return new CreateTestDrive.Response.Failure(e.getMessage());
        }
    }

    @Override
    public ApproveTestDrive.Response approve(ApproveTestDrive.Request request) {
        try {
            TestDriveRequest testDrive = findById(request.testDriveId());
            if (testDrive == null) return new ApproveTestDrive.Response.Failure("Request not found.");

            testDrive.approve();
            persistence.getTestDrives().update(testDrive);

            return new ApproveTestDrive.Response.Success(mapToDtoWithCar(testDrive));
        } catch (Exception e) {
            return new ApproveTestDrive.Response.Failure(e.getMessage());
        }
    }

    @Override
    public CompleteTestDrive.Response complete(CompleteTestDrive.Request request) {
        try {
            TestDriveRequest testDrive = findById(request.testDriveId());
            if (testDrive == null) return new CompleteTestDrive.Response.Failure("Request not found.");

            testDrive.complete();
            persistence.getTestDrives().update(testDrive);

            return new CompleteTestDrive.Response.Success(mapToDtoWithCar(testDrive));
        } catch (Exception e) {
            return new CompleteTestDrive.Response.Failure(e.getMessage());
        }
    }

    @Override
    public CancelTestDrive.Response cancel(CancelTestDrive.Request request) {
        try {
            TestDriveRequest testDrive = findById(request.testDriveId());
            if (testDrive == null) return new CancelTestDrive.Response.Failure("Request not found.");

            testDrive.cancel();
            persistence.getTestDrives().update(testDrive);

            return new CancelTestDrive.Response.Success(mapToDtoWithCar(testDrive));
        } catch (Exception e) {
            return new CancelTestDrive.Response.Failure(e.getMessage());
        }
    }

    private TestDriveRequest findById(long id) {
        return persistence.getTestDrives()
                .query(TestDriveQuery.builder().ids(List.of(new TestDriveId(id))).build())
                .stream().findFirst()
                .orElse(null);
    }

    private TestDriveDto mapToDtoWithCar(TestDriveRequest request) {
        CarInstance car = persistence.getCarInstances()
                .query(CarInstanceQuery.builder().ids(List.of(request.getCarId())).build())
                .stream().findFirst().orElse(null);

        CarInstanceDto carDto = (car != null) ? CarMappingExtensions.mapToDto(car) : null;
        return TestDriveMappingExtensions.mapToDto(request, carDto);
    }
}