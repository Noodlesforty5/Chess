package dataaccess;

import records.GameData;

import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    private HashSet<GameData> db;

    public MemoryGameDAO(){
        db = new HashSet<>(16);
    }
    @Override
    public void clear() {
        db = HashSet.newHashSet(16);
    }

    @Override
    public HashSet<GameData> listGames() {
        return db;
    }

    @Override
    public void createGame(GameData gameData) {
        db.add(gameData);
    }
    @Override
    public boolean gameExists(int gameID) throws DataAccessException {
        for(GameData game : db) {
            if (game.gameID() == gameID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        for(GameData game : db) {
            if (game.gameID() == gameID) {
                return game;
            }
        }
        throw new DataAccessException("Game not found, id: " + gameID);
    }

    @Override
    public void updateGame(GameData gameData) {
        try{
            db.remove(getGame(gameData.gameID()));
            db.add(gameData);
        }
        catch (DataAccessException e) {
            db.add(gameData);
        }

    }
}
