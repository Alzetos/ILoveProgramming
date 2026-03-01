package ru.chekhet.carshowroom.infrastructure.persistence;

import ru.chekhet.carshowroom.application.abstractions.persistence.queries.UserQuery;
import ru.chekhet.carshowroom.application.abstractions.persistence.repositories.UserRepository;
import ru.chekhet.carshowroom.domain.users.User;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {
    private final Map<UserId, User> storage = new ConcurrentHashMap<>();

    @Override
    public User add(User user) {
        storage.put(user.getId(), user);
        return user;
    }

    @Override
    public void update(User user) {
        storage.put(user.getId(), user);
    }

    @Override
    public List<User> query(UserQuery query) {
        return storage.values().stream()
                .filter(u -> query.ids() == null || query.ids().isEmpty() || query.ids().contains(u.getId()))
                .filter(u -> query.roles() == null || query.roles().isEmpty() || query.roles().contains(u.getRole()))
                .toList();
    }
}