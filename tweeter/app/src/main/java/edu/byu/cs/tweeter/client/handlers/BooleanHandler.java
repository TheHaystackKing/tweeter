package edu.byu.cs.tweeter.client.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.observers.BooleanObserver;

public class BooleanHandler extends BackgroundTaskHandler<BooleanObserver> {

    public BooleanHandler(BooleanObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccessMessage(BooleanObserver observer, Bundle data) {
        observer.handleSuccess(data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY));
    }
}
