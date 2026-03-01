package ru.chekhet.carshowroom.application.contracts.users.operations;

import ru.chekhet.carshowroom.application.contracts.users.models.UserDto;

public final class CreateUser {
    private CreateUser() {}

    public record Request(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String role
    ) {}

    public sealed interface Response {
        record Success(UserDto user) implements Response {}
        record Failure(String message) implements Response {}
    }
}