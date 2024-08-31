package edu.byu.cs.tweeter.client.observers;

import edu.byu.cs.tweeter.client.view.interfaces.ValidateView;
import edu.byu.cs.tweeter.model.domain.User;

public class ValidateObserver extends BaseObserver implements SaveUserObserver{

    ValidateView view;

    public ValidateObserver(ValidateView view) {
        super(view);
        this.view = view;
    }


    @Override
    public void handleSuccess(User user) {
        view.displayInfoToast("Hello " + user.getFirstName());
        view.clearErrorMessage();
        view.launchUserPage(user);
    }

    @Override
    public void handleFailure(String message) {
        view.displayErrorMessage(message);
    }
}
