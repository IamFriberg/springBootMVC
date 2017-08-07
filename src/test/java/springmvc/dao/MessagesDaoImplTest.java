package springmvc.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import springmvc.dto.Message;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class MessagesDaoImplTest {

    private ArrayList<Message> messages;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MessagesDaoImpl messagesDao;

    @Before
    public void setup() {
        messages = new ArrayList();
        messages.add(Message.builder().userName("user 1").message("Message 1").build());
        messages.add(Message.builder().userName("user 2").message("Message 2").build());
        messages.add(Message.builder().userName("user 3").message("Message 3").build());
    }

    @Test
    public void saveMessageOk() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString())).thenReturn(1);
        Assert.assertTrue(messagesDao.saveMessage("userName", "myMessage"));
        verify(jdbcTemplate, times(1)).update(anyString(), anyString(), anyString());
    }

    @Test
    public void saveMessageFail() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString())).thenThrow(EmptyResultDataAccessException.class);
        Assert.assertFalse(messagesDao.saveMessage("userName", "myMessage"));
        verify(jdbcTemplate, times(1)).update(anyString(), anyString(), anyString());
    }

    @Test
    public void getSingleUserMessagesOk() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyString())).thenReturn(messages);
        Assert.assertEquals(messages, messagesDao.getSingleUserMessages("User 0"));
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class), anyString());
    }

    @Test
    public void getSingleUserMessagesFail() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyString())).thenThrow(EmptyResultDataAccessException.class);
        Assert.assertTrue(messagesDao.getSingleUserMessages("User 0").isEmpty());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class), anyString());
    }

    @Test
    public void getFollowingUserMessagesOk() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyString())).thenReturn(messages);
        Assert.assertEquals(messages, messagesDao.getFollowingUserMessages("User 0"));
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class), anyString());
    }

    @Test
    public void getFollowingUserMessagesFail() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyString())).thenThrow(EmptyResultDataAccessException.class);
        Assert.assertTrue( messagesDao.getFollowingUserMessages("User 0").isEmpty());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class), anyString());
    }

}
