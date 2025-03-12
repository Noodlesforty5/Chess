package Service;

import dataaccess.*;
import records.AuthData;
import records.UserData;

import java.util.UUID;

public class UserService {

    static UserDAO userDAO;
    static AuthDAO authDAO;
   public UserService(UserDAO userDAO, AuthDAO authDAO){
       this.userDAO = userDAO;
       this.authDAO = authDAO;
   }

   public static void clear() {
        userDAO.clear();
        authDAO.clear();

   }
   public void logout(String authToken) throws UnauthorizedException {
       try {
           authDAO.getAuth(authToken);
       }
       catch (DataAccessException e){
           throw new UnauthorizedException();
       }
       authDAO.deleteAuth(authToken);
    }

    public AuthData createUser(UserData userData) {
       try {
           userDAO.createUser(userData);
       }
       catch (DataAccessException e){
            throw new BadRequestException(e.getMessage());
       }

       String authToken = UUID.randomUUID().toString();
       AuthData authData = new AuthData(userData.username(), authToken);
       authDAO.addAuth(authData);

       return authData;
    }

    public AuthData loginUser(UserData userData) throws UnauthorizedException {
       boolean authenticated;
       try {
           authenticated = userDAO.authenticateUser(userData.username(),userData.password());
        }
       catch (DataAccessException e){
           throw new UnauthorizedException();
       }
       if (authenticated){
           String authToken = UUID.randomUUID().toString();
           AuthData authData = new AuthData(userData.username(), authToken);
           authDAO.addAuth(authData);

           return authData;

        }
       else {
           throw new UnauthorizedException();
       }

   }
   public AuthData getAuth(String authToken) throws UnauthorizedException{
       try{
           return authDAO.getAuth(authToken);
       }
       catch(DataAccessException e){
           throw new UnauthorizedException();
       }
   }
}
