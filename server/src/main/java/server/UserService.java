package server;

public class UserService {
    public RegisterResult register(RegisterRequest registerRequest) {
        return UserDAO.RegisterResult;
    }
    public LoginResult login(LoginRequest loginRequest) {
        return null;
    }
    public void logout(LogoutRequest logoutRequest) {}
}
