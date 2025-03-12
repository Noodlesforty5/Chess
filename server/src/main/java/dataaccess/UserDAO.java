package dataaccess;

import records.UserData;

public interface UserDAO {

    void clear();

    void createUser(UserData userData) throws DataAccessException;

    boolean authenticateUser(String username, String password) throws DataAccessException;
}
