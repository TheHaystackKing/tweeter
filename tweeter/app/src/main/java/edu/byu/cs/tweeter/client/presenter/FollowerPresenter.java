package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.view.interfaces.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowerPresenter extends PagedPresenter<User>{

    public FollowerPresenter(PagedView<User> view) {
        super(view);
    }
    @Override
    public void getList(User user) {
        setLoading();
        new FollowService().getFollowers(user, lastItem, new GetListObserver());
    }
}
