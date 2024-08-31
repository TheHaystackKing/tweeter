package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.observers.ValidateObserver;
import edu.byu.cs.tweeter.client.view.interfaces.ValidateView;

public class LoginPresenter{

    private ValidateView view;

    public LoginPresenter(ValidateView view) {
        this.view = view;
    }

    public void initiateLogin(String username, String password) {
        String message = validateLogin(username, password);
        if (message == null) {
            view.clearErrorMessage();
            view.displayInfoToast("Logging in...");
            new UserService().login(username, password, new ValidateObserver(view));
        }

        else {
            view.displayErrorMessage(message);
        }


    }

    public String validateLogin(String username, String password) {
        if(username.length() == 0) {
            return "Aliias cannot be empty";
        }
        if (username.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (username.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }
        return null;
    }
}
