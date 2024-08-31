package edu.byu.cs.tweeter.client.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.observers.ListObserver;

public class ListHandler<T> extends BackgroundTaskHandler<ListObserver<T>>{

    public ListHandler(ListObserver<T> observer) {
        super(observer);
    }
    @Override
    protected void handleSuccessMessage(ListObserver<T> observer, Bundle data) {
        List<T> items = (List<T>) data.getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(PagedTask.MORE_PAGES_KEY);
        observer.handleSuccess(items, hasMorePages);
    }
}
