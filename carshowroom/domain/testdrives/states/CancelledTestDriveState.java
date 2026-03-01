package ru.chekhet.carshowroom.domain.testdrives.states;

import ru.chekhet.carshowroom.domain.testdrives.states.valueobjects.TestDriveStatus;

public class CancelledTestDriveState implements TestDriveState {
    @Override
    public TestDriveStatus getStatus() {
        return TestDriveStatus.CANCELLED;
    }

    @Override
    public boolean canApprove() {
        return false;
    }

    @Override
    public boolean canComplete() {
        return false;
    }

    @Override
    public boolean canCancel() {
        return false;
    }
}
