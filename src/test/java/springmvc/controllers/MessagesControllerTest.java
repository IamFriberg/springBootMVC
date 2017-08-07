package springmvc.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import springmvc.dao.MessagesDao;
import springmvc.dto.Message;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * Class for testing the logic in the controller
 */
@RunWith(SpringRunner.class)
public class MessagesControllerTest {

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private MessagesDao messagesDao;

    @InjectMocks
    private MessagesController messagesController;

    private ArrayList<Message> messages;

    @Before
    public void setup() {
        messages = new ArrayList();
        messages.add(Message.builder().userName("user 1").message("Message 1").build());
        messages.add(Message.builder().userName("user 2").message("Message 2").build());
        messages.add(Message.builder().userName("user 3").message("Message 3").build());
    }


    @Test
    public void getMyMessagesOk() {
        when(session.getAttribute(anyString())).thenReturn("username");
        when(messagesDao.getSingleUserMessages(anyString())).thenReturn(messages);

        Assert.assertEquals(messagesController.getMyMessages(session, model), "messagesOverview");

        verify(messagesDao, times(1)).getSingleUserMessages("username");
        verify(session, times(1)).getAttribute("userName");
        verify(model, times(1)).addAttribute("messages", messages);
    }

    @Test
    public void getMessagesImFollowingOk() {
        when(session.getAttribute(anyString())).thenReturn("username");
        when(messagesDao.getFollowingUserMessages(anyString())).thenReturn(messages);

        Assert.assertEquals(messagesController.getMessagesImFollowing(session, model), "followingMessagesOverview");

        verify(messagesDao, times(1)).getFollowingUserMessages("username");
        verify(session, times(1)).getAttribute("userName");
        verify(model, times(1)).addAttribute("messages", messages);
    }

    @Test
    public void saveMessageOk() {
        when(session.getAttribute(anyString())).thenReturn("username");
        when(messagesDao.saveMessage(anyString(), anyString())).thenReturn(true);

        Assert.assertEquals(messagesController.saveMessage(session, "myMessage").getStatusCode(), HttpStatus.OK);

        verify(messagesDao, times(1)).saveMessage("username", "myMessage");
        verify(session, times(1)).getAttribute("userName");
    }

    @Test
    public void saveMessageFail() {
        when(session.getAttribute(anyString())).thenReturn("username");
        when(messagesDao.saveMessage(anyString(), anyString())).thenReturn(false);

        Assert.assertEquals(messagesController.saveMessage(session, "myMessage").getStatusCode(), HttpStatus.BAD_REQUEST);

        verify(messagesDao, times(1)).saveMessage("username", "myMessage");
        verify(session, times(1)).getAttribute("userName");
    }
}
