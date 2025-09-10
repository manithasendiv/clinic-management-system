package Controllers;

import Models.User;
import ServiceLayer.RegistrationService;

public class RegistrationController {
    User ObjUser;
    RegistrationService ObjRegistrationController;

    public RegistrationController() {
        ObjRegistrationController = new RegistrationService();
    }

    public User addUser(String username, String password, String email, String role) {
        ObjUser = new User(username, password, email, role);
        return ObjUser;
    }

    public boolean addUserToDataBase() {
        return ObjRegistrationController.registerUser(ObjUser);
    }
}
