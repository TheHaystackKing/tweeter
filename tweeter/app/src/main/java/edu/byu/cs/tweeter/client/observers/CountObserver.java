package edu.byu.cs.tweeter.client.observers;

public interface CountObserver extends ServiceObserver{
    void handleSuccess(int count);
}
