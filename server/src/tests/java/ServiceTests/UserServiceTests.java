package ServiceTests;


import Service.UserService;
import dataaccess.*;
import org.junit.jupiter.api.*;
import records.UserData;



public class UserServiceTests {
    private static UserDAO userDAO;
    private static AuthDAO authDAO;
    private static UserService userService;

    @AfterAll
    public static void clear(){
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
    public void ListGamesNegTest(){

    }
    @Test
    @DisplayName("Register Positive Test")
    @Order(3)
    public void LogoutPosTest() throws DataAccessException {
        UserData user = new UserData("user", "password", "email.email@email");
        userService.createUser(user);
        assert userDAO.authenticateUser("user", "password");

    }
    @Test
    @DisplayName("Register Negative Test")
    @Order(4)
    public void LogoutNegTest(){

    }
    @Test
    @DisplayName("Register Positive Test")
    @Order(5)
    public void LoginPosTest() throws DataAccessException {
        UserData user = new UserData("user", "password", "email.email@email");
        userService.createUser(user);
        assert userDAO.authenticateUser("user", "password");

    }
    @Test
    @DisplayName("Register Negative Test")
    @Order(6)
    public void LoginNegTest(){

    }
    @Test
    @DisplayName("Register Positive Test")
    @Order(7)
    public void GetAuthTest() throws DataAccessException {
        UserData user = new UserData("user", "password", "email.email@email");
        userService.createUser(user);
        assert userDAO.authenticateUser("user", "password");

    }
    @Test
    @DisplayName("Register Negative Test")
    @Order(8)
    public void GetAuthNegTest(){

    }
}