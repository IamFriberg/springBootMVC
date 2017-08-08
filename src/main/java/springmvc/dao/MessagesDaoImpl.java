package springmvc.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import springmvc.dto.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Class that holds user related functionality
 */
@Component
public class MessagesDaoImpl implements MessagesDao {
    private Log log = LogFactory.getLog(MessagesDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String CREATE_MESSAGE = "INSERT INTO messages (username, message, timestamp) VALUES (?, ?, NOW())";
    private static final String GET_USER_MESSAGES = "SELECT username, message, timestamp FROM messages WHERE username = ? ORDER BY timestamp DESC";
    private static final String GET_OTHER_USERS_MESSAGES =
            "SELECT m.username, m.message, m.timestamp FROM db_springmvc.following f " +
                    "LEFT JOIN db_springmvc.messages m ON f.followingusername = m.username " +
                    "WHERE f.username = ? ORDER BY m.timestamp DESC;";


    /**
     * Save a message for a user
     * @param userName UserName of the user
     * @param message Message to save
     * @return If it was saved or not
     */
    @Override
    public boolean saveMessage(String userName, String message) {
        try {
            return jdbcTemplate.update(CREATE_MESSAGE, userName, message) == 1;
        } catch (DataAccessException e) {
            log.error("Could not save message for user: " + userName);
            return false;
        }
    }

    /**
     * Get all the message for a specific user
     * @param userName UserName of the user
     * @return A list of all the messages
     */
    @Override
    public List<Message> getSingleUserMessages(String userName) {
        try {
            return jdbcTemplate.query(GET_USER_MESSAGES, new MessageMapper(), userName);
        } catch (DataAccessException e) {
            log.error(e);
            log.error("Could Not fetch messages for user: " + userName);
            return Collections.emptyList();
        }
    }

    /**
     * Get all messages the users a user are following.
     * @param userName User that follows other users
     * @return A list of all the messages
     */
    @Override
    public List<Message> getFollowingUserMessages(String userName) {
        try {
            return jdbcTemplate.query(GET_OTHER_USERS_MESSAGES, new MessageMapper(), userName);
        } catch (DataAccessException e) {
            log.error(e);
            log.error("Could Not fetch other users messages for user: " + userName);
            return Collections.emptyList();
        }
    }

    /**
     * RowMapper for mapping a database row to a message object
     */
    private static final class MessageMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Message.builder()
                    .userName(rs.getString("username"))
                    .message(rs.getString("message"))
                    .timeStamp(rs.getTimestamp("timestamp"))
                    .build();
        }
    }
}
