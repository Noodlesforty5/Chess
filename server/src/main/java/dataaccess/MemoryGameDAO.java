package dataaccess;

import models.GameData;
import models.UserData;

import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    private HashSet<UserData> db;

    public MemoryGameDAO(){
        db = HashSet.newHashSet(16);
    }
    @Override
    public void clear() {
        db = HashSet.newHashSet(16);
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
