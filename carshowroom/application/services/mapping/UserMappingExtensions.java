package ru.chekhet.carshowroom.application.services.mapping;

import ru.chekhet.carshowroom.application.contracts.users.models.UserDto;
import ru.chekhet.carshowroom.domain.users.User;

public class UserMappingExtensions {
    public static UserDto mapToDto(User user) {
        return new UserDto(
                user.getId().value(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().value(),
                user.getRole().name()
        );
    }
}