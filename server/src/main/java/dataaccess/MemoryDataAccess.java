package dataaccess;

import models.AuthData;
import models.GameData;
import models.UserData;

import java.util.HashMap;
import java.util.Collection;
import java.util.HashSet;

public class MemoryDataAccess implements GameDAO, AuthDAO, UserDAO {
    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public void addAuth(AuthData authData) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void createUser(UserData userData) throws DataAccessException {

    }

    @Override
    public boolean authenticateUser(String username, String password) throws DataAccessException {
        return false;
    }

    @Override
    public HashSet<GameData> listGames() {
        return null;
    }

    @Override
    public void createGame(GameData gameData) {

    }

    @Override
    public boolean gameExists(int gameID) {
        return false;
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public void updateGame(GameData gameData) {

    }
}
