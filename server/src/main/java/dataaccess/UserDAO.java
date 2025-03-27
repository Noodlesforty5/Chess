package dataaccess;

import records.UserData;

public interface UserDAO {

    void clear();

    UserData createUser(UserData userData) throws DataAccessException;

    boolean authenticateUser(String username, String password) throws DataAccessException;
}
