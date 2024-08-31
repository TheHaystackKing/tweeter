package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.observers.ValidateObserver;
import edu.byu.cs.tweeter.client.view.interfaces.ValidateView;

public class RegisterPresenter {

    private ValidateView view;

    public RegisterPresenter (ValidateView view) {
        this.view = view;
    }

    public void initiateRegister(String firstName, String lastName, String username, String password, String imageBase64Code) {
        String message = validateRegistration(firstName,lastName, username,password, imageBase64Code);
        if(message == null) {
            view.clearErrorMessage();
            view.displayInfoToast("Registering...");
            new UserService().register(firstName, lastName, username,password, imageBase64Code, new ValidateObserver(view));
        }
        else {
            view.displayErrorMessage(message);
        }

    }

    public String validateRegistration(String firstName, String lastName, String username, String password, String imageBase64Code) {
        if (firstName.length() == 0) {
            return "First Name cannot be empty.";
        }
        if (lastName.length() == 0) {
            return "Last Name cannot be empty.";
        }
        if (username.length() == 0) {
            return "Alias cannot be empty.";
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
        if (imageBase64Code == null) {
            return "Profile image must be uploaded.";
        }
        return null;
    }
}
