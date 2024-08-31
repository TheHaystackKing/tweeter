package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.observers.BaseObserver;
import edu.byu.cs.tweeter.client.observers.GetUserObserver;
import edu.byu.cs.tweeter.client.observers.ListObserver;
import edu.byu.cs.tweeter.client.view.interfaces.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> {

    PagedView<T> view;

    protected T lastItem;

    private boolean hasMorePages;
    protected boolean isLoading = false;

    public PagedPresenter(PagedView<T> view) {
        this.view = view;
    }

    public void setLoading() {
        isLoading = true;
        view.setLoadingFooter(true);
    }

    public abstract void getList(User user);

    public boolean isLoading() {
        return isLoading;
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public void getUser(String userAlias) {
        view.displayInfoToast("Getting user's profile...");
        new UserService().getUser(userAlias, new NavigateToUserObserver());
    }

    protected class GetListObserver implements ListObserver<T> {

        @Override
        public void handleSuccess(List<T> values, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            lastItem = (values.size() > 0) ? values.get(values.size() - 1) : null;
            view.addItems(values);
            PagedPresenter.this.hasMorePages = hasMorePages;
        }

        @Override
        public void handleFailure(String message) {
            view.setLoadingFooter(false);
            view.displayErrorToast(message);
        }
    }

    public class NavigateToUserObserver extends BaseObserver implements GetUserObserver {

        public NavigateToUserObserver() {
            super(view);
        }

        @Override
        public void handleSuccess(User user) {
            view.launchUserPage(user);
        }
    }

}
