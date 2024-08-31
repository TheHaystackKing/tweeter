package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusTest {

    private MainPresenter.MainView mockView;
    private StatusService mockStatusService;

    private MainPresenter mainPresenterSpy;
    private ArgumentCaptor<Status> argument;

    @BeforeEach
    public void setup() {
        mockView = Mockito.mock(MainPresenter.MainView.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mainPresenterSpy = Mockito.spy(new MainPresenter(mockView));
        Mockito.when(mainPresenterSpy.getStatusService()).thenReturn(mockStatusService);
        argument = ArgumentCaptor.forClass(Status.class);
    }

    @Test
    public void postStatusSuccess() {
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                SimpleNotificationObserver observer = invocation.getArgument(1, SimpleNotificationObserver.class);
                observer.handleSuccess();
                return null;
            }
        };
        makePost(answer);
        verifyParameter(argument);
        Mockito.verify(mockView).displayInfoToast("Posting Status...");
        Mockito.verify(mockView).displayInfoToast("Successfully Posted!");
    }

    @Test
    public void postStatusFailure() {
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                SimpleNotificationObserver observer = invocation.getArgument(1, SimpleNotificationObserver.class);
                observer.handleFailure("Operation failed: Something Bad Happened");
                return null;
            }
        };
        makePost(answer);
        verifyParameter(argument);
        verifyResult("Operation failed: Something Bad Happened");
    }

    @Test
    public void postStatusException() {
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                SimpleNotificationObserver observer = invocation.getArgument(1, SimpleNotificationObserver.class);
                observer.handleFailure("Operation failed because of exception: Some exception");
                return null;
            }
        };
        makePost(answer);
        verifyParameter(argument);
        verifyResult("Operation failed because of exception: Some exception");
    }

    private void makePost(Answer<Void> answer) {
        Mockito.doAnswer(answer).when(mockStatusService).postStatus(argument.capture(), Mockito.any());
        mainPresenterSpy.makePost("Making a post");
    }

    private void verifyResult(String s) {
        Mockito.verify(mockView).displayInfoToast("Posting Status...");
        Mockito.verify(mockView).displayErrorToast(s);
    }

    private void verifyParameter(ArgumentCaptor<Status> argument) {
        Status status = argument.getValue();
        Assertions.assertNotNull(status.datetime);
        Assertions.assertNotNull(status.mentions);
        Assertions.assertNotNull(status.urls);
        Assertions.assertNotNull(status.user);
        Assertions.assertEquals(status.post, "Making a post");
    }



}
