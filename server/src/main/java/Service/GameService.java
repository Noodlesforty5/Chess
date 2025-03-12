package Service;
import chess.ChessBoard;
import chess.ChessGame;
import dataaccess.*;
import models.AuthData;
import models.GameData;

import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class GameService {
    static GameDAO gameDAO;
    static AuthDAO authDAO;

    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public static void clear() {
        gameDAO.clear();
    }

    public HashSet<GameData> listGames(String authToken) throws UnauthorizedException {
        try{
            authDAO.getAuth(authToken);
        }
        catch (DataAccessException e){
            throw new UnauthorizedException();
        }

        return gameDAO.listGames();
    }

    public int createGame(String authToken, String gameName) throws UnauthorizedException, DataAccessException {
        try{
            authDAO.getAuth(authToken);
        }
        catch (DataAccessException e){
            throw new UnauthorizedException();
        }
        int gameID;
        do {
            gameID = ThreadLocalRandom.current().nextInt(1,1000);
        }
        while (gameDAO.gameExists(gameID));
        try{
            ChessGame game = new ChessGame();
            ChessBoard board = new ChessBoard();
            board.resetBoard();
            game.setBoard(board);
            gameDAO.createGame(new GameData(gameID, null, null, gameName, game));
        }
        catch (DataAccessException e){
            throw new BadRequestException(e.getMessage());
        }
        return gameID;
    }

    public boolean joinGame(String authToken, String playerColor, int gameID) throws UnauthorizedException, DataAccessException {


        GameData gameData;
        AuthData authData;
        authData = authDAO.getAuth(authToken);

        try {
            authDAO.getAuth(authToken);
        }
        catch (DataAccessException e) {
            throw new UnauthorizedException();
        }
        try {
            gameData = gameDAO.getGame(gameID);
        }
        catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }

        String whiteUser = gameData.whiteUsername();
        String blackUser = gameData.blackUsername();

        if(Objects.equals(playerColor, "WHITE")){
            if(whiteUser != null && !whiteUser.equals(authData.username()))return false;
            else whiteUser = authData.username();
        }
        else if(Objects.equals(playerColor, "BLACK")){
            if(blackUser != null && !blackUser.equals(authData.username()))return false;
            else blackUser = authData.username();
        }
        else if(playerColor != "BLACK" || playerColor != "WHITE"){
            throw new BadRequestException("%s is not a valid team color".formatted(playerColor));
        }
            gameDAO.updateGame(new GameData(gameID, whiteUser,blackUser, gameData.gameName(), gameData.game()));
        return true;
    }
    public GameData getGameData(String authToken, int gameID) throws UnauthorizedException {
        try {
            authDAO.getAuth(authToken);
        }
        catch (DataAccessException e) {
            throw new UnauthorizedException();
        }
        try {
            return gameDAO.getGame(gameID);
        }
        catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }


    }
    public void updateGmae(String authToken, GameData gameData) throws UnauthorizedException {
        try {
            authDAO.getAuth(authToken);
        }
        catch (DataAccessException e) {
            throw new UnauthorizedException();
        }

        gameDAO.updateGame(gameData);

    }
}
//join game, create game, list games