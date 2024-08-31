package edu.byu.cs.tweeter.client.observers;

import java.util.List;

public interface ListObserver<T> extends ServiceObserver{
    void handleSuccess(List<T> values, boolean hasMorePages);
}
