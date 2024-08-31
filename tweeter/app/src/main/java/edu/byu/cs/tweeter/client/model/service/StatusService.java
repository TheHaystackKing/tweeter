package edu.byu.cs.tweeter.client.model.service;

import static edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils.runTask;

import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.handlers.ListHandler;
import edu.byu.cs.tweeter.client.handlers.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.observers.ListObserver;
import edu.byu.cs.tweeter.client.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;


public class StatusService {

    private static final int PAGE_SIZE = 10;

    public void getFeed(User user, Status lastStatus, ListObserver<Status> observer) {
        GetFeedTask getFeedTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(),
                user, PAGE_SIZE, lastStatus, new ListHandler<>(observer));
        runTask(getFeedTask);
    }

    public void getStory(User user, Status lastStatus, ListObserver<Status> observer) {
        GetStoryTask getStoryTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
                user, PAGE_SIZE, lastStatus, new ListHandler<>(observer));
        runTask(getStoryTask);
    }

    public void postStatus(Status newStatus, SimpleNotificationObserver observer) {
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new SimpleNotificationHandler(observer));
        runTask(statusTask);
    }
}
