package dataaccess;

import records.UserData;

public class SQLUserDAO implements UserDAO{
    @Override
    public void clear() {

    }

    @Override
    public void createUser(UserData userData) throws DataAccessException {

    }

    @Override
    public boolean authenticateUser(String username, String password) throws DataAccessException {
        return false;
    }
}
