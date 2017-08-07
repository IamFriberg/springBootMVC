package springmvc.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import springmvc.dto.User;


import java.util.ArrayList;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserDaoImplTest {

    private final static String USERNAME = "user";
    private final static String PASSWORD = "password";


    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserDaoImpl userDaoImpl;

    @Test
    public void createUserOk() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString())).thenReturn(1);
        User user = User.builder().userName(USERNAME).password(PASSWORD).build();
        Assert.assertTrue(userDaoImpl.createUser(user));
        verify(jdbcTemplate, times(1)).update(anyString(), anyString(), anyString());
    }

    @Test
    public void createUserFail() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString())).thenThrow(EmptyResultDataAccessException.class);
        User user = User.builder().userName(USERNAME).password(PASSWORD).build();
        Assert.assertFalse(userDaoImpl.createUser(user));
        verify(jdbcTemplate, times(1)).update(anyString(), anyString(), anyString());
    }

    @Test
    public void authenticateUserOk() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class) , anyString(), anyString())).thenReturn(1);
        User user = User.builder().userName(USERNAME).password(PASSWORD).build();
        Assert.assertTrue(userDaoImpl.authenticateUser(user));
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class), anyString(), anyString());
    }

    @Test
    public void authenticateUserFail() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), anyString(), anyString())).thenThrow(EmptyResultDataAccessException.class);
        User user = User.builder().userName(USERNAME).password(PASSWORD).build();
        Assert.assertFalse(userDaoImpl.authenticateUser(user));
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class), anyString(), anyString());
    }

    @Test
    public void getUsersOk() {
        ArrayList users = new ArrayList();
        users.add("User 1");
        users.add("User 2");
        when(jdbcTemplate.queryForList(anyString(), eq(String.class), anyString())).thenReturn(users);
        Assert.assertEquals(users, userDaoImpl.getUsers("User 0"));
        verify(jdbcTemplate, times(1)).queryForList(anyString(), eq(String.class), anyString());
    }

    @Test
    public void getUsersFail() {
        when(jdbcTemplate.queryForList(anyString(), eq(String.class), anyString())).thenThrow(EmptyResultDataAccessException.class);
        Assert.assertTrue(userDaoImpl.getUsers("User 0").isEmpty());
        verify(jdbcTemplate, times(1)).queryForList(anyString(), eq(String.class), anyString());
    }
}
