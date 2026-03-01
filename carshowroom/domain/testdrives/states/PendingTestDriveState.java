package ru.chekhet.carshowroom.domain.testdrives.states;

import ru.chekhet.carshowroom.domain.testdrives.states.valueobjects.TestDriveStatus;

public class PendingTestDriveState implements TestDriveState {
    @Override
    public TestDriveStatus getStatus() {
        return TestDriveStatus.PENDING;
    }

    @Override
    public boolean canApprove() {
        return true;
    }

    @Override
    public boolean canComplete() {
        return false;
    }

    @Override
    public boolean canCancel() {
        return true;
    }
}
