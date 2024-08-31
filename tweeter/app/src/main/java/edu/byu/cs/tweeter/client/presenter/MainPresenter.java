package edu.byu.cs.tweeter.client.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.observers.BaseObserver;
import edu.byu.cs.tweeter.client.observers.BooleanObserver;
import edu.byu.cs.tweeter.client.observers.CountObserver;
import edu.byu.cs.tweeter.client.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.view.interfaces.View;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter {

    //----------------View Interface----------------//

    public interface MainView extends View {

        void displayFollowerCount(String count);
        void clearFollowerCount();

        void displayFollowingCount(String count);
        void clearFollowingCount();

        void displayIsFollower();
        void displayIsNotFollower();

        void logoutUser();

    }

    //----------------Main Functions----------------//

    MainView view;
    User currUser;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    protected StatusService getStatusService() {
        return new StatusService();
    }

    protected Status createStatus(String post) {
        try {
            return new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));
        }
        catch(Exception ex) {
            view.displayErrorToast("Failed to post the status because of exception: " + ex.getMessage());
        }
        return null;
    }

    public void checkFollowerStatus(User user) {
        new FollowService().isFollowing(user, new IsFollowerObserver(view));
    }

    public void follow(User user) {
        currUser = user;
        view.displayInfoToast("Adding " + user.getName() + "...");
        new FollowService().follow(user, new FollowObserver(view));
    }

    public void unfollow(User user) {
        currUser = user;
        view.displayInfoToast("Removing " + user.getName() + "...");
        new FollowService().unfollow(user, new UnfollowObserver(view));
    }

    public void initiateFollowCount(User user) {
        new FollowService().getFollowerCount(user, new GetFollowerCountObserver(view));
        new FollowService().getFollowingCount(user, new GetFollowingCountObserver(view));
    }

    public void logout() {
        view.displayInfoToast("Logging Out...");
        new UserService().logout(new LogoutObserver(view));
    }

    public void makePost(String post) {
        view.displayInfoToast("Posting Status...");
        Status newStatus = createStatus(post);

        /*try {
            newStatus = new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));
        }
        catch(Exception ex) {
            view.displayErrorToast("Failed to post the status because of exception: " + ex.getMessage());
        }*/
        getStatusService().postStatus(newStatus,new PostStatusObserver(view));
    }

    //----------------Helper Functions----------------//

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }


    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    //----------------Observer Implementations----------------//

    private class PostStatusObserver extends BaseObserver implements SimpleNotificationObserver {

        PostStatusObserver(View view) {
            super(view);
        }

        @Override
        public void handleSuccess() {
            view.displayInfoToast("Successfully Posted!");
        }
    }

    private class IsFollowerObserver extends BaseObserver implements BooleanObserver {

        protected IsFollowerObserver(View view) {
            super(view);
        }

        @Override
        public void handleSuccess(boolean result) {
            if(result) {
                view.displayIsFollower();
            }
            else {
                view.displayIsNotFollower();
            }
        }
    }

    private class GetFollowerCountObserver extends BaseObserver implements CountObserver {

        protected GetFollowerCountObserver(View view) {
            super(view);
        }

        @Override
        public void handleSuccess(int count) {
            view.clearFollowerCount();
            view.displayFollowerCount("Followers: " + String.valueOf(count));
        }
    }

    private class GetFollowingCountObserver extends BaseObserver implements CountObserver {

        protected GetFollowingCountObserver(View view) {
            super(view);
        }

        @Override
        public void handleSuccess(int count) {
            view.clearFollowingCount();
            view.displayFollowingCount("Following: "+ String.valueOf(count));
        }
    }

    private class LogoutObserver extends BaseObserver implements SimpleNotificationObserver {

        protected LogoutObserver(View view) {
            super(view);
        }

        @Override
        public void handleSuccess() {
            Cache.getInstance().clearCache();
            view.logoutUser();
        }
    }
    private class FollowObserver extends BaseObserver implements SimpleNotificationObserver {

        protected FollowObserver(View view) {
            super(view);
        }

        @Override
        public void handleSuccess() {
            initiateFollowCount(currUser);
            view.displayIsFollower();
        }
    }

    private class UnfollowObserver extends BaseObserver implements SimpleNotificationObserver {

        protected UnfollowObserver(View view) {
            super(view);
        }

        @Override
        public void handleSuccess() {
            initiateFollowCount(currUser);
            view.displayIsNotFollower();
        }
    }
}
