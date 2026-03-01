package ru.chekhet.carshowroom.application.contracts.users.models;

public record UserDto(
        long id,
        String firstName,
        String lastName,
        String email,
        String role
) {}
