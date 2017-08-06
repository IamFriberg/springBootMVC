package springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springmvc.dto.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.List;

/**
 * Class that holds user related functionality
 */
@Component
public class UserDaoImpl implements UserDao {
    private Log log = LogFactory.getLog(UserDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String CREATE_USER = "INSERT INTO users (username, password) VALUES (?, ?)";
    private static final String VERIFY_USER = "SELECT count(*) FROM users WHERE username = ? AND password = ?";
    private static final String GET_USERS = "SELECT username FROM users WHERE username != ?";

    /**
     * Creates a user in the database
     * @param user
     * @return
     */
    @Override
    public boolean createUser(User user) {
        //TODO this is a big NO NO, we should use a SHA-1 algorithm or similar.
        try {
            return jdbcTemplate.update(CREATE_USER, user.userName, user.password) == 1;
        } catch (DataAccessException e) {
            log.error("User already exists: " + user.userName);
            return false;
        }
    }

    /**
     * Authenticate a user against the database
     * @param user User object
     * @return
     */
    @Override
    public boolean authenticateUser(User user) {
        try {
            return jdbcTemplate.queryForObject(VERIFY_USER, Integer.class, user.userName, user.password).equals(1);
        } catch (DataAccessException e) {
            log.error("User could not be authenticated: " + user.userName);
            return false;
        }
    }

    /**
     * Fetch all other users
     * @param userName The username we should exclude
     * @return A list of all other registered users
     */
    @Override
    public List<String> getUsers(String userName) {
        // TODO We should identify a user by unique id so we can add functionality to change username.
        try {
            return jdbcTemplate.queryForList(GET_USERS, String.class, userName);
        } catch (DataAccessException e) {
            log.error("Could Not fetch users for: " + userName);
            return Collections.emptyList();
        }
    }
}
