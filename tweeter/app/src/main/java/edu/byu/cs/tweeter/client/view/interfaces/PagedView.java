package edu.byu.cs.tweeter.client.view.interfaces;

import java.util.List;

public interface PagedView<T> extends View{

    void setLoadingFooter(boolean value);
    void addItems(List<T> items);
}
