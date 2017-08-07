package springmvc.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import springmvc.dao.UserDao;
import springmvc.dto.User;

import javax.servlet.http.HttpSession;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Class for testing the logic in the controller
 */
@RunWith(SpringRunner.class)
public class RegistrationControllerTest {

    @Mock
    private HttpSession session;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private RegistrationController registrationController;

    @Test
    public void createUserOk() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userDao.createUser(any(User.class))).thenReturn(true);

        User user = User.builder().userName("user").password("password").build();
        Assert.assertEquals(registrationController.createUser(session, user, bindingResult).getViewName(), "overview");

        verify(userDao, times(1)).createUser(any(User.class));
        verify(session, times(1)).setAttribute("userName", user.getUserName());
    }

    @Test
    public void createUserHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        User user = User.builder().userName("user").password("password").build();
        Assert.assertEquals(registrationController.createUser(session, user, bindingResult).getViewName(), "registrationForm");

        verify(userDao, times(0)).createUser(any(User.class));
        verify(session, times(0)).setAttribute("userName", user.getUserName());
    }

    @Test
    public void createUserCanNotRegisterUser() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userDao.createUser(any(User.class))).thenReturn(false);

        User user = User.builder().userName("user").password("password").build();
        Assert.assertEquals(registrationController.createUser(session, user, bindingResult).getViewName(), "registrationForm");

        verify(userDao, times(1)).createUser(any(User.class));
        verify(session, times(0)).setAttribute("userName", user.getUserName());
    }
}
