package dataaccess;

import java.util.HashSet;
import records.UserData;

public class MemoryUserDAO implements UserDAO {

    private HashSet<UserData> db;

    public MemoryUserDAO(){
        db = HashSet.newHashSet(16);
    }
    @Override
    public void clear() {
        db = HashSet.newHashSet(16);
    }

    @Override
    public void createUser(UserData userData) throws DataAccessException {
        try{
            getUser(userData.username());
        }
        catch (DataAccessException e){
            db.add(userData);
            return;
        }

        throw new DataAccessException("User already exists:  " + userData.username());

    }

    private UserData getUser(String username) throws DataAccessException {
        for (UserData user : db) {
            if (user.username().equals(username)){
                return user;
            }
        }
    throw new DataAccessException("User not found: " + username);
    }

    @Override
    public boolean authenticateUser(String username, String password) throws DataAccessException {
        boolean userExists = false;
        for(UserData user : db) {
            if (user.username().equals(username)) {
                userExists = true;
            }
            if (user.password().equals(password) && (user.username().equals(username))) {
                return true;
            }
        }
        if (userExists) {
            return false;
        }
        else{
            throw new DataAccessException("User does not exist" + username);
        }
    }
}
