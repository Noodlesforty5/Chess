package dataaccess;


import records.GameData;

import java.util.HashSet;

public interface GameDAO {
    void clear();

    HashSet<GameData> listGames();

    void createGame(GameData gameData) throws DataAccessException;

    boolean gameExists(int gameID) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    void updateGame(GameData gameData);
}
