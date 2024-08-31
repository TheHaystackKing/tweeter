package edu.byu.cs.tweeter.client.observers;

public interface BooleanObserver extends ServiceObserver {
    void handleSuccess(boolean result);
}
