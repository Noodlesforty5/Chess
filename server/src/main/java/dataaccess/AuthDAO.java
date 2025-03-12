package dataaccess;

import models.AuthData;

public interface AuthDAO {
    void clear();

    void deleteAuth(String authToken);

    AuthData getAuth(String authToken);

    void addAuth(AuthData authData);
}
