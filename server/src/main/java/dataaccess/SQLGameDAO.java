package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import records.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{

    public SQLGameDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        var statement = "TRUNCATE game";
        executeUpdate(statement);

    }
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  game (
              `gameID` INT NOT NULL,
              `whiteName` VARCHAR(256) NOT NULL,
              `blackName` VARCHAR(256) NOT NULL,
              `gameName`, VARCHAR(256) NOT NULL,
              `game` TEXT NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`),
              INDEX(whiteName),
              INDEX(blackName),
              INDEX(gameName),
              INDEX(game)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private void executeUpdate(String statement, Object... params) {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param instanceof ChessGame p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public HashSet<GameData> listGames() {
        var result = new HashSet<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, json FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public void createGame(GameData gameData) throws DataAccessException {
        var statement = "INSERT INTO game (gameID, whiteName, blackName, gameName, game, json) VALUES (?, ?, ?, ?, ?, ?)";
        var json = new Gson().toJson(gameData);
        executeUpdate(statement, gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), gameData.game(), json);



    }

    @Override
    public boolean gameExists(int gameID) throws DataAccessException {
        var gameInfo = getGame(gameID);
        return gameInfo != null;
        }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, json FROM game WHERE gameID = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var id = rs.getInt("gameID");
        var json = rs.getString("json");
        var gameData = new Gson().fromJson(json, GameData.class);
        return new GameData(id, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), gameData.game());
    }

    @Override
    public void updateGame(GameData gameData) throws DataAccessException {
        int gameID  = gameData.gameID();
        var statement = "DELETE FROM game WHERE gameID = ?";
        executeUpdate(statement, gameID);
        createGame(gameData);
    }
}
