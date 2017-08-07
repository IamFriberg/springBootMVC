package springmvc.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import springmvc.dao.UserDao;
import springmvc.dto.User;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Class for testing the logic in the controller
 */
@RunWith(SpringRunner.class)
public class UsersControllerTest {

    @Mock
    HttpSession session;

    @Mock
    Model model;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UsersController usersController;

    @Test
    public void getUsersOk() {
        ArrayList users = new ArrayList();
        users.add("User 1");
        users.add("User 2");

        when(session.getAttribute(anyString())).thenReturn("username");
        when(userDao.getUsers(anyString())).thenReturn(users);

        Assert.assertEquals(usersController.getUsers(session, model), "usersOverview");

        verify(userDao, times(1)).getUsers("username");
        verify(session, times(1)).getAttribute("userName");
        verify(model, times(1)).addAttribute("users", users);
    }
}
