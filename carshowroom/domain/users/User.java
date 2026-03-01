package ru.chekhet.carshowroom.domain.users;

import lombok.Builder;
import lombok.Value;
import ru.chekhet.carshowroom.domain.users.valueobjects.Email;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserRole;

@Value
@Builder
public class User {
    UserId id;
    String firstName;
    String lastName;
    Email email;
    UserRole role;
}