package ServiceTests;

import Service.GameService;
import dataaccess.GameDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


public class GameServiceTests {
    private static GameDAO gameDAO;
    private static GameService gameService;


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
}
