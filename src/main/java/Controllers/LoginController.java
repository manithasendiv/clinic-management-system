package Controllers;

import Models.User;
import ServiceLayer.LoginService;

public class LoginController {
    User ObjUser;
    LoginService ObjLoginService;

    public LoginController() {
        ObjLoginService = new LoginService();
    }

    public User validateUser(String username, String password) {
        ObjUser = ObjLoginService.validateCredentials(username, password);
        return ObjUser;
    }
}
