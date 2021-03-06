package springmvc.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springmvc.dao.MessagesDao;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller that handles everything message related
 */
@Controller
public class MessagesController {

    private Log log = LogFactory.getLog(MessagesController.class);

    @Autowired
    private MessagesDao messagesDao;

    /**
     * Method for fetching the overview with the users own messages
     * @param session The session so we can see that the user is logged in
     * @param model Data model that is used to populate the view
     * @return The view to show
     */
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String getMyMessages(HttpSession session, Model model) {
        List messages = messagesDao.getSingleUserMessages(session.getAttribute("userName").toString());
        model.addAttribute("messages", messages);
        return "messagesOverview";
    }

    /**
     * Method for fetching the overview with the messages from the user the user follows
     * @param session The session so we can see that the user is logged in
     * @param model Data model that is used to populate the view
     * @return The view to show
     */
    @RequestMapping(value = "/followingmessages", method = RequestMethod.GET)
    public String getMessagesImFollowing(HttpSession session, Model model) {
        List messages = messagesDao.getFollowingUserMessages(session.getAttribute("userName").toString());
        model.addAttribute("messages", messages);
        return "followingMessagesOverview";
    }


    /**
     *  Method that saves a message from a user
     * @param session The session so we can see witch user is logged in
     * @param message The message to save
     * @return The http status OK or BAD_REQUEST
     */
    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public ResponseEntity saveMessage(HttpSession session, @RequestParam("message") String message) {
        if (messagesDao.saveMessage(session.getAttribute("userName").toString(), message)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
