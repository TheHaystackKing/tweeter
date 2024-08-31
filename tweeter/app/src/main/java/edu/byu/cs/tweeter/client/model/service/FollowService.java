package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.handlers.BooleanHandler;
import edu.byu.cs.tweeter.client.handlers.CountHandler;
import edu.byu.cs.tweeter.client.handlers.ListHandler;
import edu.byu.cs.tweeter.client.handlers.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.observers.BooleanObserver;
import edu.byu.cs.tweeter.client.observers.CountObserver;
import edu.byu.cs.tweeter.client.observers.ListObserver;
import edu.byu.cs.tweeter.client.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {

    private static final int PAGE_SIZE = 10;

    public void getFollowing(User user, User lastFollowee, ListObserver<User> observer) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(Cache.getInstance().getCurrUserAuthToken(),
                user, PAGE_SIZE, lastFollowee, new ListHandler<>(observer));
        BackgroundTaskUtils.runTask(getFollowingTask);
    }

    public void getFollowers(User user, User lastFollower, ListObserver<User> observer) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
                user, PAGE_SIZE, lastFollower, new ListHandler<>(observer));
        BackgroundTaskUtils.runTask(getFollowersTask);
    }

    public void getFollowerCount(User selectedUser, CountObserver observer) {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new CountHandler(observer));
        BackgroundTaskUtils.runTask(followersCountTask);
    }

    public void getFollowingCount(User selectedUser, CountObserver observer) {
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(), selectedUser,
                new CountHandler(observer));
        BackgroundTaskUtils.runTask(followingCountTask);

    }

    public void isFollowing(User selectedUser, BooleanObserver observer ) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new BooleanHandler(observer));
        BackgroundTaskUtils.runTask(isFollowerTask);
    }

    public void unfollow(User selectedUser, SimpleNotificationObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new SimpleNotificationHandler(observer));
        BackgroundTaskUtils.runTask(unfollowTask);
    }

    public void follow(User selectedUser, SimpleNotificationObserver observer) {
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new SimpleNotificationHandler(observer));
        BackgroundTaskUtils.runTask(followTask);
    }
}
