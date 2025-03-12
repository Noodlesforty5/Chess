package server;

import Service.GameService;
import com.google.gson.Gson;
import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import models.GameData;
import models.GameList;
import spark.Request;
import spark.Response;

public class GameHandler {

    GameService gameService;

    public GameHandler(GameService gameService){
        this.gameService = gameService;
    }
    public Object listGames(Request request, Response response) throws UnauthorizedException {
        String authToken = request.headers("authorization");
        GameList games = new GameList(gameService.listGames(authToken));
        response.status(200);
        return new Gson().toJson(games);
    }

    public Object createGame(Request request, Response response) throws UnauthorizedException, DataAccessException {
        if (!request.body().contains("\"gameName\":")){
            throw new BadRequestException("No gameName provided");
        }
        GameData gameData = new Gson().fromJson(request.body(), GameData.class);

        String authToken = request.headers("authorization");
        int gameID = gameService.createGame(authToken, gameData.gameName());

        response.status(200);
        return "{ \"gameID\": %d }".formatted(gameID);

    }

    public Object joinGame(Request request, Response response) throws UnauthorizedException, DataAccessException {
        if (!request.body().contains("\"gameID\":")){
            throw new BadRequestException("No gameID provided");
        }
        String authToken = request.headers("authorization");
        record joinGameData(String playerColor, int gameID){}
        joinGameData joinGameData = new Gson().fromJson(request.body(), joinGameData.class);
        boolean joinSuccess = gameService.joinGame(authToken, joinGameData.playerColor, joinGameData.gameID);

        if(joinSuccess){
            response.status(403);
            return "{ \"message\": \"Error already taken\" }";
        }
        response.status(200);
        return "{}";
    }
}
