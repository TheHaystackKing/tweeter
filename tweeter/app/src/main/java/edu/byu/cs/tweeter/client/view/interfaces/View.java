package edu.byu.cs.tweeter.client.view.interfaces;

import edu.byu.cs.tweeter.model.domain.User;

public interface View {

    void displayInfoToast(String message);
    void displayErrorToast(String message);

    void launchUserPage(User user);
}
