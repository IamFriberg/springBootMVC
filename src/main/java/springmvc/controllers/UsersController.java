package springmvc.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springmvc.dao.UserDao;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller that handles interaction between users
 */
@Controller
public class UsersController {

    private Log log = LogFactory.getLog(UsersController.class);

    @Autowired
    private UserDao userDao;

    /**
     *  Method that returns the view with all other users
     * @param session The session so we can see witch user is logged in
     * @param model the model so we can add the other users
     * @return The view to show
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(HttpSession session, Model model) {
        List users = userDao.getUsers(session.getAttribute("userName").toString());
        model.addAttribute("users", users);
        return "usersOverview";
    }

}
