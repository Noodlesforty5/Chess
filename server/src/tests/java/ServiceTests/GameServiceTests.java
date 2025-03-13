package ServiceTests;

import Service.GameService;
import Service.UserService;
import dataaccess.*;
import org.junit.jupiter.api.*;


public class GameServiceTests {
    private static GameDAO gameDAO;
    private static AuthDAO authDAO;
    private static GameService gameService;

    @AfterAll
    public static void clear(){
        authDAO.clear();
        gameDAO.clear();
    }
    @BeforeEach
    public void indiclear(){
        authDAO.clear();
        gameDAO.clear();
    }
    @BeforeAll
    public static void init(){
        gameDAO = new MemoryGameDAO();
        authDAO = new MemoryAuthDAO();
        gameService = new GameService(gameDAO, authDAO);

    }


    @Test
    @DisplayName("List Games Positive Test")
    @Order(1)
    public void ListGamesPosTest(){


    }
    @Test
    @DisplayName("List Games Negative Test")
    @Order(2)
    public void ListGamesNegTest(){

    }
    @Test
    @DisplayName("List Games Positive Test")
    @Order(1)
    public void JoinPosTest(){


    }
    @Test
    @DisplayName("List Games Negative Test")
    @Order(2)
    public void JoinNegTest(){

    }
    @Test
    @DisplayName("List Games Positive Test")
    @Order(1)
    public void CreatePosTest(){


    }
    @Test
    @DisplayName("List Games Negative Test")
    @Order(2)
    public void CreateNegTest(){

    }
    @Test
    @DisplayName("List Games Positive Test")
    @Order(1)
    public void UpdatePosTest(){


    }
    @Test
    @DisplayName("List Games Negative Test")
    @Order(2)
    public void UpdateNegTest(){

    }
}
