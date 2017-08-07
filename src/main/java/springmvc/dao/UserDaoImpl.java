package springmvc.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import springmvc.dto.FollowUser;
import springmvc.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private static final String CREATE_USER_FOLLOWING = "INSERT INTO following (username, followingusername) VALUES (?, ?)";
    private static final String DELETE_USER_FOLLOWING = "DELETE FROM following WHERE username = ? AND followingusername = ?";
    private static final String VERIFY_USER = "SELECT count(*) FROM users WHERE username = ? AND password = ?";
    private static final String GET_USERS =
            "SELECT u.username, (f.username IS NOT NULL) AS follow  FROM users u " +
                    "LEFT JOIN (SELECT * FROM following WHERE username = ?) " +
                    "AS f ON u.username = f.followingusername WHERE u.username != ? ORDER BY u.username DESC;";

    /**
     * Creates a user in the database
     * @param user
     * @return
     */
    @Override
    public boolean createUser(User user) {
        // TODO this is a big NO NO, we should use a SHA-1 algorithm or similar and not save plain text.
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
        // TODO Same thing here, big NO NO, we should use a SHA-1 algorithm or similar and not save plain text.
        try {
            return jdbcTemplate.queryForObject(VERIFY_USER, Integer.class, user.userName, user.password).equals(1);
        } catch (DataAccessException e) {
            log.error("User could not be authenticated: " + user.userName);
            return false;
        }
    }

    @Override
    public boolean followUser(String userName, String userNameToFollow) {
        try {
            return jdbcTemplate.update(CREATE_USER_FOLLOWING, userName, userNameToFollow) == 1;
        } catch (DataAccessException e) {
            log.error("User already exists: " + userName);
            return false;
        }
    }

    @Override
    public boolean unFollowUser(String userName, String userNameToFollow) {
        try {
            return jdbcTemplate.update(DELETE_USER_FOLLOWING, userName, userNameToFollow) == 1;
        } catch (DataAccessException e) {
            log.error("User already exists: " + userName);
            return false;
        }
    }

    /**
     * Fetch all other users
     * @param userName The username we should exclude
     * @return A list of all other registered users
     */
    @Override
    public List<FollowUser> getUsers(String userName) {
        // TODO We should identify a user by unique id so we can add functionality to change username.
        try {
            return jdbcTemplate.query(GET_USERS, new FollowUserMapper(), userName, userName);
        } catch (DataAccessException e) {
            log.error("Could Not fetch users for: " + userName);
            return Collections.emptyList();
        }
    }
    private static final class FollowUserMapper implements RowMapper<FollowUser> {
        @Override
        public FollowUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            return FollowUser.builder()
                    .userName(rs.getString("username"))
                    .follow(rs.getBoolean("follow"))
                    .build();
        }
    }


}
