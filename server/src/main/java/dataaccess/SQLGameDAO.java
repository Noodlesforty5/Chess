package dataaccess;

import records.GameData;

import java.util.HashSet;

public class SQLGameDAO implements GameDAO{
    @Override
    public void clear() {

    }

    @Override
    public HashSet<GameData> listGames() {
        return null;
    }

    @Override
    public void createGame(GameData gameData) throws DataAccessException {

    }

    @Override
    public boolean gameExists(int gameID) throws DataAccessException {
        return false;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(GameData gameData) {

    }
}
