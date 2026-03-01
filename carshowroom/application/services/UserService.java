package ru.chekhet.carshowroom.application.services;

import lombok.RequiredArgsConstructor;
import ru.chekhet.carshowroom.application.abstractions.persistence.IPersistenceContext;
import ru.chekhet.carshowroom.application.abstractions.persistence.queries.UserQuery;
import ru.chekhet.carshowroom.application.contracts.users.IUserService;
import ru.chekhet.carshowroom.application.contracts.users.operations.CreateUser;
import ru.chekhet.carshowroom.application.contracts.users.operations.GetUserDetails;
import ru.chekhet.carshowroom.application.services.mapping.UserMappingExtensions;
import ru.chekhet.carshowroom.domain.users.User;
import ru.chekhet.carshowroom.domain.users.valueobjects.Email;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserId;
import ru.chekhet.carshowroom.domain.users.valueobjects.UserRole;

import java.util.List;

@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IPersistenceContext persistence;

    @Override
    public CreateUser.Response createUser(CreateUser.Request request) {
        try {
            User user = User.builder()
                    .id(new UserId(System.currentTimeMillis()))
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(new Email(request.email()))
                    .role(UserRole.valueOf(request.role().toUpperCase()))
                    .build();

            persistence.getUsers().add(user);

            return new CreateUser.Response.Success(UserMappingExtensions.mapToDto(user));
        } catch (Exception e) {
            return new CreateUser.Response.Failure(e.getMessage());
        }
    }

    @Override
    public GetUserDetails.Response getUserDetails(GetUserDetails.Request request) {
        try {
            User user = persistence.getUsers()
                    .query(UserQuery.builder()
                            .ids(List.of(new UserId(request.userId())))
                            .build())
                    .stream()
                    .findFirst()
                    .orElse(null);

            if (user == null) {
                return new GetUserDetails.Response.Failure("User not found.");
            }

            return new GetUserDetails.Response.Success(UserMappingExtensions.mapToDto(user));
        } catch (Exception e) {
            return new GetUserDetails.Response.Failure(e.getMessage());
        }
    }
}