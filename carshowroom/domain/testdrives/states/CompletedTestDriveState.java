package ru.chekhet.carshowroom.domain.testdrives.states;

import ru.chekhet.carshowroom.domain.testdrives.states.valueobjects.TestDriveStatus;

public class CompletedTestDriveState implements TestDriveState {
    @Override
    public TestDriveStatus getStatus() {
        return TestDriveStatus.COMPLETED;
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
