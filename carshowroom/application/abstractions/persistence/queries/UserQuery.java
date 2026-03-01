package ru.chekhet.carshowroom.application.abstractions.persistence.queries;

import lombok.Builder;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserRole;

import java.util.List;

@Builder
public record UserQuery(
        List<UserId> ids,
        List<UserRole> roles
) {}
