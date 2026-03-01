package ru.chekhet.carshowroom.application.contracts.testdrives;

import ru.chekhet.carshowroom.application.contracts.testdrives.operations.*;

public interface ITestDriveService {
    CreateTestDrive.Response create(CreateTestDrive.Request request);
    ApproveTestDrive.Response approve(ApproveTestDrive.Request request);
    CompleteTestDrive.Response complete(CompleteTestDrive.Request request);
    CancelTestDrive.Response cancel(CancelTestDrive.Request request);
}