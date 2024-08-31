package edu.byu.cs.tweeter.client.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.AuthenticateTask;
import edu.byu.cs.tweeter.client.observers.GetUserObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserHandler extends BackgroundTaskHandler<GetUserObserver> {

    public GetUserHandler(GetUserObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccessMessage(GetUserObserver observer, Bundle data) {
        User user = (User) data.getSerializable(AuthenticateTask.USER_KEY);
        observer.handleSuccess(user);
    }
}
