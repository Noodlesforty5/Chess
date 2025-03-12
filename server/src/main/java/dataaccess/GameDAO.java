package dataaccess;


import models.GameData;

import java.util.HashSet;

public interface GameDAO {
    void clear();

    HashSet<GameData> listGames();

    void createGame(GameData gameData);

    boolean gameExists(int gameID);

    GameData getGame(int gameID);

    void updateGame(GameData gameData);
}
