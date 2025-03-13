package ServiceTests;


import Service.UserService;
import dataaccess.*;
import org.junit.jupiter.api.*;
import records.AuthData;
import records.UserData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class UserServiceTests {
    private static UserDAO userDAO;
    private static AuthDAO authDAO;
    private static UserService userService;

    @AfterAll
    public static void clear(){
        authDAO.clear();
        userDAO.clear();
    }
    @BeforeEach
    public void indiclear(){
        authDAO.clear();
        userDAO.clear();
    }
    @BeforeAll
    public static void init(){
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        userService = new UserService(userDAO, authDAO);

    }
    @Test
    @DisplayName("Register Positive Test")
    @Order(1)
    public void RegisterPosTest() throws DataAccessException {
        UserData user = new UserData("user", "password", "email.email@email");
        userService.createUser(user);
        assert userDAO.authenticateUser("user", "password");

    }
    @Test
    @DisplayName("Register Negative Test")
    @Order(2)
    public void RegisterNegTest(){
        UserData user = new UserData("user", "password", "email");
        userService.createUser(user);
        boolean thrown = false;
        try{
            userService.createUser(user);

        }
        catch (BadRequestException e){
            thrown = true;
        }
        assert thrown;


    }
    @Test
    @DisplayName("Logout Positive Test")
    @Order(3)
    public void LogoutPosTest() throws DataAccessException, UnauthorizedException {
        UserData user = new UserData("user", "password", "email.email@email");
        userService.createUser(user);
        AuthData authData = userService.loginUser(user);
        boolean success = false;
        try{
            userService.logout(authData.authToken());
            success = true;
        }
        catch (UnauthorizedException e){
        }
        assert success;



    }
    @Test
    @DisplayName("Logout Negative Test")
    @Order(4)
    public void LogoutNegTest() throws UnauthorizedException {
        UserData user = new UserData("user", "password", "email.email@email");
        userService.createUser(user);
        AuthData authData = userService.loginUser(user);
        userService.logout(authData.authToken());

        boolean thrown = false;
        try{
            userService.logout(authData.authToken());
        }
        catch (UnauthorizedException e){
            thrown = true;
        }
        assert thrown;

    }
    @Test
    @DisplayName("Login Positive Test")
    @Order(5)
    public void LoginPosTest() throws DataAccessException, UnauthorizedException {
        UserData user = new UserData("user", "password", "email.email@email");
        userService.createUser(user);
        AuthData auth = userService.loginUser(user);
        assertEquals(auth, authDAO.getAuth(auth.authToken()));


    }
    @Test
    @DisplayName("Login Negative Test")
    @Order(6)
    public void LoginNegTest() throws UnauthorizedException, DataAccessException {
        UserData user = new UserData("user", "password", "email.email@email");
        boolean thrown = false;
        try{
            userService.loginUser(user);
        }
        catch (UnauthorizedException e){
            thrown = true;
        }
        assert thrown;
    }
    @Test
    @DisplayName("GetAuth Positive Test")
    @Order(7)
    public void GetAuthTest() throws DataAccessException, UnauthorizedException {
        UserData user = new UserData("user", "password", "email.email@email");
        userService.createUser(user);
        AuthData auth = userService.loginUser(user);

        assertEquals(auth, authDAO.getAuth(auth.authToken()));

    }
    @Test
    @DisplayName("GetAuth Negative Test")
    @Order(8)
    public void GetAuthNegTest() throws UnauthorizedException, DataAccessException {
        UserData user = new UserData("user", "password", "email.email@email");
        userService.createUser(user);
        AuthData auth = userService.loginUser(user);
        userService.logout(auth.authToken());
        boolean thrown = false;
        try{
            authDAO.getAuth(auth.authToken());
        }
        catch (DataAccessException e){
            thrown = true;
        }
        assert thrown;

    }
    @Test
    @DisplayName("Clear Positive Test")
    @Order(7)
    public void ClearTest() throws DataAccessException, UnauthorizedException {
        UserData user = new UserData("user", "password", "email.email@email");
        userService.createUser(user);
        AuthData auth = userService.loginUser(user);

        assertEquals(auth, authDAO.getAuth(auth.authToken()));

    }
    @Test
    @DisplayName("Clear Negative Test")
    @Order(8)
    public void ClearNegTest() throws UnauthorizedException, DataAccessException {
        UserData user = new UserData("user", "password", "email.email@email");
        userService.createUser(user);
        AuthData auth = userService.loginUser(user);
        userService.logout(auth.authToken());
        boolean thrown = false;
        try{
            authDAO.getAuth(auth.authToken());
        }
        catch (DataAccessException e){
            thrown = true;
        }
        assert thrown;

    }

}