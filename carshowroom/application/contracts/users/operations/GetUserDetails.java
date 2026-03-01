package ru.chekhet.carshowroom.application.contracts.users.operations;

import ru.chekhet.carshowroom.application.contracts.users.models.UserDto;

public final class GetUserDetails {
    public record Request(long userId) {}

    public sealed interface Response {
        record Success(UserDto user) implements Response {}
        record Failure(String message) implements Response {}
    }
}
