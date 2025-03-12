package dataaccess;

import models.AuthData;
import models.UserData;

import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO {
    private HashSet<UserData> db;

    public MemoryAuthDAO(){
        db = HashSet.newHashSet(16);
    }
    @Override
    public void clear() {
        db = HashSet.newHashSet(16);
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public void addAuth(AuthData authData) {

    }


}

