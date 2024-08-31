package edu.byu.cs.tweeter.client.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.observers.SimpleNotificationObserver;

public class SimpleNotificationHandler extends BackgroundTaskHandler<SimpleNotificationObserver> {

    public SimpleNotificationHandler(SimpleNotificationObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccessMessage(SimpleNotificationObserver observer, Bundle data) {
        observer.handleSuccess();
    }
}
