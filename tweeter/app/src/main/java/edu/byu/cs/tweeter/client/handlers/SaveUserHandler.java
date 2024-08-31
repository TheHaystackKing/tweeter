package edu.byu.cs.tweeter.client.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.AuthenticateTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.observers.SaveUserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class SaveUserHandler extends BackgroundTaskHandler<SaveUserObserver> {

    public SaveUserHandler(SaveUserObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccessMessage(SaveUserObserver observer, Bundle data) {
        User loggedInUser = (User) data.getSerializable(AuthenticateTask.USER_KEY);
        AuthToken authToken = (AuthToken) data.getSerializable(AuthenticateTask.AUTH_TOKEN_KEY);

        // Cache user session information
        Cache.getInstance().setCurrUser(loggedInUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);
        observer.handleSuccess(loggedInUser);
    }
}
