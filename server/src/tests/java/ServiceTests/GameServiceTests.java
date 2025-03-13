package ServiceTests;

import Service.GameService;
import Service.UserService;
import chess.ChessGame;
import dataaccess.*;
import org.junit.jupiter.api.*;
import records.AuthData;
import records.GameData;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


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
    public void ListGamesPosTest() throws DataAccessException, UnauthorizedException {
        HashSet<GameData> expected = new HashSet<>();
        GameData gameData;
        int gameID;
        gameID = gameService.createGame("authToken", "cheese");
        gameData = gameService.getGameData("authToken", gameID);
        expected.add(gameData);
        HashSet<GameData> list = gameDAO.listGames();

        assertEquals(list, expected);




    }
    @Test
    @DisplayName("List Games Negative Test")
    @Order(2)
    public void ListGamesNegTest() throws DataAccessException, UnauthorizedException {
        HashSet<GameData> expected = new HashSet<>();
        GameData gameData;
        gameData = new GameData(3, null, null, "gameName", new ChessGame());
        expected.add(gameData);
        gameService.createGame("authToken", "cheese");
        HashSet<GameData> list = gameDAO.listGames();
        assertNotEquals(list, expected);
    }
    @Test
    @DisplayName("List Games Positive Test")
    @Order(3)
    public void JoinPosTest() throws DataAccessException, UnauthorizedException {
        int gameID;
        AuthData authData;
        authData = new AuthData("Lord", "authToken");
        authDAO.addAuth(authData);
        gameID = gameService.createGame("authToken", "cheese");
        assert gameService.joinGame("authToken", "WHITE", gameID);


    }
    @Test
    @DisplayName("List Games Negative Test")
    @Order(4)
    public void JoinNegTest() throws UnauthorizedException, DataAccessException {
        int gameID;
        AuthData authData;
        authData = new AuthData("Lord", "authToken");
        authDAO.addAuth(authData);
        gameID = gameService.createGame("authToken", "cheese");
        boolean caught = false;
        try {
            gameService.joinGame("authToken", "WHITE", 3);
        }
        catch (BadRequestException e){
            caught = true;
        }
        try {
            gameService.joinGame("authTokenBAD", "WHITE", gameID);
        }
        catch (UnauthorizedException e){
            caught = true;
        }
        assert caught;

    }
    @Test
    @DisplayName("Create Positive Test")
    @Order(5)
    public void CreatePosTest() throws DataAccessException, UnauthorizedException {
        GameData gameData;
        AuthData authData;
        authData = new AuthData("Lord", "authToken");
        gameData = new GameData(3, null, null, "gameName", new ChessGame());
        authDAO.addAuth(authData);
        gameService.createGame("authToken", "cheese");
        boolean success = false;
        try{
            gameDAO.getGame(3);
            success = true;
        }
        catch (DataAccessException e){}


    }
    @Test
    @DisplayName("Create Negative Test")
    @Order(6)
    public void CreateNegTest() throws DataAccessException, UnauthorizedException {
        GameData gameData;
        AuthData authData;
        authData = new AuthData("Lord", "authToken");
        gameData = new GameData(3, null, null, "gameName", new ChessGame());
        authDAO.addAuth(authData);
        gameService.createGame("authToken", "cheese");
        boolean success = false;
        try{
            gameDAO.getGame(3);

        }
        catch (DataAccessException e){success = true;}

    }
    @Test
    @DisplayName("Update Positive Test")
    @Order(7)
    public void UpdatePosTest() throws UnauthorizedException, DataAccessException {
        int gameID;
        AuthData authData;
        GameData gameData;
        authData = new AuthData("Lord", "authToken");
        authDAO.addAuth(authData);
        gameID = gameService.createGame("authToken", "gameName");

        GameData expected = new GameData(gameID, "WHITE", "BLACK", "gameName", new ChessGame());


        gameData = new GameData(gameID, "WHITE", "BLACK", "gameName", new ChessGame());
        authDAO.addAuth(authData);
        gameService.updateGame(authData.authToken(), gameData);
        assertEquals(gameService.getGameData("authToken", gameID), expected);

    }
    @Test
    @DisplayName("Update Negative Test")
    @Order(8)
    public void UpdateNegTest() throws UnauthorizedException, DataAccessException {
        int gameID;
        AuthData authData;
        authData = new AuthData("Lord", "authToken");
        authDAO.addAuth(authData);

        gameID = gameService.createGame("authToken", "gameName");

        GameData expected = new GameData(gameID, "WHITE", "BLACK", "gameName", new ChessGame());
        GameData gameData;

        authData = new AuthData("Lord", "authToken");
        gameData = new GameData(gameID, "WHITE", "BLACK", "gameName", new ChessGame());

        boolean caught = false;
        try {
            gameService.updateGame("peep", gameData);
        }
        catch (UnauthorizedException e){
            caught = true;
        }
        assert caught;

    }
}
