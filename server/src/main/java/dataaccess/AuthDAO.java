package dataaccess;

import records.AuthData;

public interface AuthDAO {
    void clear();

    void deleteAuth(String authToken);

    AuthData getAuth(String authToken) throws DataAccessException;

    void addAuth(AuthData authData);
}
//test,super test
