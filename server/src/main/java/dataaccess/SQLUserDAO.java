package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import org.mindrot.jbcrypt.BCrypt;
import records.GameData;
import records.UserData;
import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLUserDAO implements UserDAO{


    public SQLUserDAO() throws DataAccessException {
        configureDatabase();
    }
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  game (
              `name` TEXT NOT NULL,
              `password` VARCHAR(256) NOT NULL,
              `email` VARCHAR(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`name`),
              INDEX(password),
              INDEX(email),
              INDEX(json)
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
    public void clear() {
        var statement = "TRUNCATE user";
        executeUpdate(statement);

    }

    @Override
    public UserData createUser(UserData userData) throws DataAccessException {
        String clearTextPassword = userData.password();
        String hashedPassword = BCrypt.hashpw(clearTextPassword, BCrypt.gensalt());
        var statement = "INSERT INTO user (name, password, email, json) VALUES (?,?,?,?)";
        var json = new Gson().toJson(userData);
        executeUpdate(statement, userData.username(), hashedPassword, userData.email(), json);
        return new UserData(userData.username(), clearTextPassword, userData.email());

    }

    @Override
    public boolean authenticateUser(String username, String password) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT name, password, json FROM user WHERE name = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try( var rs = ps.executeQuery()){
                    if (rs.next()){
                        return readUser(rs, password, username);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private boolean readUser(ResultSet rs, String plainPassword, String username) throws SQLException {
        var password = rs.getString("password");
        return BCrypt.checkpw(plainPassword, password);


        //var json = rs.getString("json");
        //var userData = new Gson().fromJson(json, UserData.class);

    }
}
