package springmvc.dao;

import springmvc.dto.User;

import java.util.List;

public interface UserDao {

    boolean createUser(User user);

    boolean authenticateUser(User user);

    List<String> getUsers(String userName);
}
