package ru.chekhet.carshowroom.domain.testdrives.states;

import ru.chekhet.carshowroom.domain.testdrives.states.valueobjects.TestDriveStatus;

public class ApprovedTestDriveState implements TestDriveState {
    @Override
    public TestDriveStatus getStatus() {
        return TestDriveStatus.APPROVED;
    }

    @Override
    public boolean canApprove() {
        return false;
    }

    @Override
    public boolean canComplete() {
        return true;
    }

    @Override
    public boolean canCancel() {
        return true;
    }
}
