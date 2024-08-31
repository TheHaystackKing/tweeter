package edu.byu.cs.tweeter.client.observers;

import edu.byu.cs.tweeter.client.view.interfaces.View;

public class BaseObserver implements ServiceObserver{

    View view;

    protected BaseObserver(View view) {
        this.view = view;
    }

    @Override
    public void handleFailure(String message) {
        view.displayErrorToast(message);
    }
}
