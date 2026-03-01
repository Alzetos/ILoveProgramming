package ru.chekhet.carshowroom.application.contracts.users;

import ru.chekhet.carshowroom.application.contracts.users.operations.CreateUser;
import ru.chekhet.carshowroom.application.contracts.users.operations.GetUserDetails;

public interface IUserService {
    CreateUser.Response createUser(CreateUser.Request request);
    GetUserDetails.Response getUserDetails(GetUserDetails.Request request);
}