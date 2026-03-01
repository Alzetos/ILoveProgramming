package ru.chekhet.carshowroom.application.contracts.testdrives.models;

import ru.chekhet.carshowroom.application.contracts.cars.models.CarInstanceDto;
import java.time.LocalDateTime;

public record TestDriveDto(
        long id,
        long clientId,
        CarInstanceDto car,
        LocalDateTime scheduledTime,
        String status
) {}