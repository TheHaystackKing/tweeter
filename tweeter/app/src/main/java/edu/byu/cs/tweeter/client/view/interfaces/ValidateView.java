package edu.byu.cs.tweeter.client.view.interfaces;

public interface ValidateView extends View {

    void displayErrorMessage(String message);
    void clearErrorMessage();
}
