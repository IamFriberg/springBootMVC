package springmvc.dao;

import springmvc.dto.Message;

import java.util.List;

public interface MessagesDao {

    boolean saveMessage(String userName, String message);

    List<Message> getSingleUserMessages(String userName);

    List<Message> getFollowingUserMessages(String userName);

}
