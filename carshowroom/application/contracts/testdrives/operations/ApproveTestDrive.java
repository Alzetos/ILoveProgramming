package ru.chekhet.carshowroom.application.contracts.testdrives.operations;

import ru.chekhet.carshowroom.application.contracts.testdrives.models.TestDriveDto;

public final class ApproveTestDrive {
    private ApproveTestDrive() {}

    public record Request(long testDriveId) {}

    public sealed interface Response {
        record Success(TestDriveDto testDrive) implements Response {}
        record Failure(String message) implements Response {}
    }
}