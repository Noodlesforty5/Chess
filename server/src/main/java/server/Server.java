package server;

import dataaccess.*;
import spark.*;
import Service.*;



public class Server {

    UserDAO userDAO;
    GameDAO gameDAO;
    AuthDAO authDAO;

    static GameService gameService;
    static UserService userService;

    UserHandler userHandler;
    GameHandler gameHandler;



    public Server(){

        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();

        userService = new UserService(userDAO, authDAO);
        gameService = new GameService(gameDAO, authDAO);

        userHandler = new UserHandler(userService);
        gameHandler = new GameHandler(gameService);

    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db",this::clear);
        Spark.post("/user", userHandler::register);
        Spark.post("/session", userHandler::login);
        Spark.delete("/session", userHandler::logout);
        Spark.get("/game", gameHandler::listGames);
        Spark.post("/game", gameHandler::createGame);
        Spark.put("/game", gameHandler::joinGame);

        Spark.exception(BadRequestException.class, this::badRequestExceptionHandler);
        Spark.exception(UnauthorizedException.class, this::unauthorizedExceptionHandler);
        Spark.exception(GenericException.class, this::genericExceptionHandler);


        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object clear(Request request, Response response) {
        clearDB();

        response.status(200);
        return "{}";
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    public void clearDB(){
        UserService.clear();
        GameService.clear();
    }

    private void badRequestExceptionHandler(BadRequestException ex, Request req, Response resp){
        resp.status(400);
        resp.body("{ \"message\": \"Error: bad request\" }" );
    }
    private void unauthorizedExceptionHandler(UnauthorizedException ex, Request req, Response resp){
        resp.status(401);
        resp.body("{ \"message\": \"Error: unauthorized\" }" );
    }
    private void genericExceptionHandler(GenericException ex, Request req, Response resp){
        resp.status(500);
        resp.body("{ \"message\": \"Error: %s\" }".formatted(ex.getMessage()));
    }

}
//clear
