package edu.byu.cs.tweeter.client.presenter;


import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.view.interfaces.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends PagedPresenter<User> {

    public FollowingPresenter(PagedView<User> view) {
        super(view);
    }

    @Override
    public void getList(User user) {
        setLoading();
        new FollowService().getFollowing(user, lastItem, new GetListObserver());
    }
}
