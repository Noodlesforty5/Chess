package dataaccess;

import com.google.gson.Gson;
import records.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDao implements AuthDAO {
    
    
    public SQLAuthDao() throws DataAccessException {
        configureDatabase();
    }



    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `authToken` int NOT NULL,
              `userName` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`authToken`),
              INDEX(userName)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
    private void configureDatabase() throws  DataAccessException {
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
    @Override
    public void clear() {
        var statement = "TRUNCATE auth";
        executeUpdate(statement);
        
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
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAuth(String authToken) {
       var statement = "DELETE FROM auth WHERE authToken = ?";
       executeUpdate(statement, authToken);

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, json FROM auth WHERE authToken = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try( var rs = ps.executeQuery()){
                    if (rs.next()){
                        return readAuth(rs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

        private AuthData readAuth(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authToken");
        var json = rs.getString("json");
        var authData = new Gson().fromJson(json, AuthData.class);
        return new AuthData(authData.username(), authToken);
    }

    @Override
    public void addAuth(AuthData authData) {
        var statement = "INSERT INTO game (authToken, username) VALUES (?, ?)";
        var json = new Gson().toJson(authData);
        executeUpdate(statement, authData.authToken(), authData.username(), json);


    }
}
