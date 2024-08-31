package edu.byu.cs.tweeter.client.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.GetCountTask;
import edu.byu.cs.tweeter.client.observers.CountObserver;

public class CountHandler extends BackgroundTaskHandler<CountObserver>{

    public CountHandler(CountObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(CountObserver observer, Bundle data) {
        observer.handleSuccess(data.getInt(GetCountTask.COUNT_KEY));
    }
}
