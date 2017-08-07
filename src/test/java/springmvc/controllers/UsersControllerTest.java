package springmvc.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import springmvc.dao.UserDao;
import springmvc.dto.FollowUser;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * Class for testing the logic in the controller
 */
@RunWith(SpringRunner.class)
public class UsersControllerTest {

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UsersController usersController;

    private ArrayList<FollowUser> users;

    @Before
    public void setup() {
        users = new ArrayList();
        users.add(FollowUser.builder().userName("user 1").follow(false).build());
        users.add(FollowUser.builder().userName("user 2").follow(true).build());
        users.add(FollowUser.builder().userName("user 3").follow(false).build());
    }


    @Test
    public void getUsersOk() {
        when(session.getAttribute(anyString())).thenReturn("username");
        when(userDao.getUsers(anyString())).thenReturn(users);

        Assert.assertEquals(usersController.getUsers(session, model), "usersOverview");

        verify(userDao, times(1)).getUsers("username");
        verify(session, times(1)).getAttribute("userName");
        verify(model, times(1)).addAttribute("users", users);
    }

    @Test
    public void followUserOk() {
        when(session.getAttribute(anyString())).thenReturn("username");
        when(userDao.followUser(anyString(), anyString())).thenReturn(true);

        Assert.assertEquals(usersController.followUser(session, "folloUserName").getStatusCode(), HttpStatus.OK);

        verify(userDao, times(1)).followUser("username", "folloUserName");
        verify(session, times(1)).getAttribute("userName");
    }

    @Test
    public void followUserFail() {
        when(session.getAttribute(anyString())).thenReturn("username");
        when(userDao.followUser(anyString(), anyString())).thenReturn(false);

        Assert.assertEquals(usersController.followUser(session, "folloUserName").getStatusCode(), HttpStatus.BAD_REQUEST);

        verify(userDao, times(1)).followUser("username", "folloUserName");
        verify(session, times(1)).getAttribute("userName");
    }

    @Test
    public void unFollowUserOk() {
        when(session.getAttribute(anyString())).thenReturn("username");
        when(userDao.unFollowUser(anyString(), anyString())).thenReturn(true);

        Assert.assertEquals(usersController.unFollowUser(session, "folloUserName").getStatusCode(), HttpStatus.OK);

        verify(userDao, times(1)).unFollowUser("username", "folloUserName");
        verify(session, times(1)).getAttribute("userName");
    }

    @Test
    public void unFollowUserFail() {
        when(session.getAttribute(anyString())).thenReturn("username");
        when(userDao.unFollowUser(anyString(), anyString())).thenReturn(false);

        Assert.assertEquals(usersController.unFollowUser(session, "folloUserName").getStatusCode(), HttpStatus.BAD_REQUEST);

        verify(userDao, times(1)).unFollowUser("username", "folloUserName");
        verify(session, times(1)).getAttribute("userName");
    }

}
