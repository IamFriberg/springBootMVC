package springmvc.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springmvc.dao.UserDao;
import springmvc.dto.User;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Controller that handles all requests related to the login
 */
@Controller
public class LoginController {

    private Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    private UserDao userDao;

    /**
     * Returns the login page
     * @param user  User information
     * @return The view to show
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(User user) {
        return "loginForm";
    }

    /**
     * Endpoint to submit the login form
     * @param session The session so we can add that the user is logged in
     * @param user User information
     * @param bindingResult Contains any validation errors
     * @return The view to show
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView checkUserInfo(HttpSession session, @Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        // Check if user has any fields that failed the validation
        if (bindingResult.hasErrors()) {
            log.debug("Login form has errors");
            // redirect the user back to the same page to display the errors
            modelAndView.setViewName("loginForm");
        } else {
            if (userDao.authenticateUser(user)) {
                log.info("User authenticated");
                session.setAttribute("userName", user.userName);
                // TODO add details to the session.
                modelAndView.setViewName("overview");
            } else {
                // TODO add error to display
                log.error("Could not authenticate user");
                modelAndView.setViewName("loginForm");
            }

        }
        return modelAndView;
    }

    /**
     * Endpoint to log out the user
     * @param session The session so we know witch user to log out
     * @return The view to show
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String loginOut(HttpSession session) {
        session.invalidate();
        log.info("User logged out");
        return "overview";
    }
}
