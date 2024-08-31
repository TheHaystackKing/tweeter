package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.view.interfaces.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends PagedPresenter<Status> {

    public FeedPresenter(PagedView<Status> view) {
        super(view);
    }

    @Override
    public void getList(User user) {
        setLoading();
        new StatusService().getFeed(user, lastItem, new GetListObserver());
    }
}
