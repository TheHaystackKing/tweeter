package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.handlers.GetUserHandler;
import edu.byu.cs.tweeter.client.handlers.SaveUserHandler;
import edu.byu.cs.tweeter.client.handlers.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.observers.GetUserObserver;
import edu.byu.cs.tweeter.client.observers.SaveUserObserver;
import edu.byu.cs.tweeter.client.observers.SimpleNotificationObserver;

public class UserService {

    public void login(String username, String password, SaveUserObserver observer) {
        LoginTask loginTask = new LoginTask(username, password, new SaveUserHandler(observer));
        BackgroundTaskUtils.runTask(loginTask);

    }

    public void register(String firstName, String lastName, String username, String password, String imageBase64Code, SaveUserObserver observer) {
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                username, password, imageBase64Code, new SaveUserHandler(observer));
        BackgroundTaskUtils.runTask(registerTask);
    }

    public void getUser(String userAlias, GetUserObserver observer) {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new GetUserHandler(observer));
        BackgroundTaskUtils.runTask(getUserTask);
    }

    public void logout(SimpleNotificationObserver observer) {
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new SimpleNotificationHandler(observer));
        BackgroundTaskUtils.runTask(logoutTask);
    }
}
