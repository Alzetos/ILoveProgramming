package ru.chekhet.carshowroom.application.abstractions.persistence.repositories;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.UserQuery;
import ru.chekhet.carshowroom.domain.users.User;

import java.util.List;

public interface UserRepository {
    User add(User user);
    void update(User user);
    List<User> query(UserQuery query);
}
