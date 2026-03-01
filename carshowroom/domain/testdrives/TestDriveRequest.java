package ru.chekhet.carshowroom.domain.testdrives;

import lombok.Getter;
import ru.chekhet.carshowroom.domain.cars.valueobjects.CarId;
import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;
import ru.chekhet.carshowroom.domain.testdrives.states.*;
import ru.chekhet.carshowroom.domain.testdrives.states.valueobjects.TestDriveStatus;
import ru.chekhet.carshowroom.domain.testdrives.valueobjects.TestDriveId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;

import java.time.LocalDateTime;

@Getter
public class TestDriveRequest {
    private final TestDriveId id;
    private final UserId clientId;
    private final CarId carId;
    private final LocalDateTime scheduledTime;
    private TestDriveState state;

    public TestDriveRequest(TestDriveId id, UserId clientId, CarId carId, LocalDateTime scheduledTime) {
        if (scheduledTime.isBefore(LocalDateTime.now())) {
            throw new DomainValidationException("Cannot schedule a test drive in the past");
        }

        this.id = id;
        this.clientId = clientId;
        this.carId = carId;
        this.scheduledTime = scheduledTime;
        this.state = new PendingTestDriveState();
    }

    public void approve() {
        if (!state.canApprove()) {
            throw new DomainValidationException("Cannot Approve on state " + state.getStatus());
        }
        this.state = new ApprovedTestDriveState();
    }

    public void cancel() {
        if (!state.canCancel()) {
            throw new DomainValidationException("Cannot cancel on state " + state.getStatus());
        }
        this.state = new CancelledTestDriveState();
    }

    public void complete() {
        if (!state.canComplete()) {
            throw new DomainValidationException("Cannot complete on state " + state.getStatus());
        }
        this.state = new CompletedTestDriveState();
    }
}
