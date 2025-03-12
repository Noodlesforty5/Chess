package server;

import Service.UserService;
import com.google.gson.Gson;
import dataaccess.BadRequestException;
import dataaccess.UnauthorizedException;
import models.AuthData;
import models.UserData;
import spark.Request;
import spark.Response;

public class UserHandler {

    UserService userService;

    public UserHandler(UserService userService){
        this.userService = userService;
    }
    public Object register(Request request, Response response) {
        UserData userData = new Gson().fromJson(request.body(),UserData.class);

        if (userData.username() == null || userData.password() == null){
            throw new BadRequestException("No username and or password given");
            }
        try {
            AuthData authData = userService.createUser(userData);
            response.status(200);
            return new Gson().toJson(authData);
        } catch (BadRequestException e){
            response.status(403);
            return "{\"message\": \"Error: already taken\"}";
        }

    }


    public Object login(Request request, Response response) throws UnauthorizedException {
        UserData userData = new Gson().fromJson(request.body(),UserData.class);
        AuthData authData = userService.loginUser(userData);

        response.status(200);
        return new Gson().toJson(authData);
    }

    public Object logout(Request req, Response response) throws UnauthorizedException {
        String authToken = req.headers("authorization");
        userService.logout(authToken);
        response.status(200);
        return "{}";
    }
}
