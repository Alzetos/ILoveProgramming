package ru.chekhet.carshowroom.application.contracts.testdrives.operations;

import ru.chekhet.carshowroom.application.contracts.testdrives.models.TestDriveDto;
import java.time.LocalDateTime;

public final class CreateTestDrive {
    private CreateTestDrive() {}

    public record Request(
            long clientId,
            long carId,
            LocalDateTime scheduledTime
    ) {}

    public sealed interface Response {
        record Success(TestDriveDto testDrive) implements Response {}
        record Failure(String message) implements Response {}
    }
}