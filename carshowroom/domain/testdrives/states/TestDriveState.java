package ru.chekhet.carshowroom.domain.testdrives.states;

import ru.chekhet.carshowroom.domain.testdrives.states.valueobjects.TestDriveStatus;

public interface TestDriveState {
    public TestDriveStatus getStatus();

    boolean canApprove();
    boolean canComplete();
    boolean canCancel();
}
