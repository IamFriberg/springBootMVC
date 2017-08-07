package springmvc.dao;

import springmvc.dto.FollowUser;
import springmvc.dto.User;

import java.util.List;

public interface UserDao {

    boolean createUser(User user);

    boolean authenticateUser(User user);

    boolean followUser(String userName, String userNameToFollow);

    boolean unFollowUser(String userName, String userNameToFollow);

    List<FollowUser> getUsers(String userName);
}
