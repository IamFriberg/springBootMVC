package springmvc.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springmvc.dao.UserDao;
import springmvc.dao.UserDaoImpl;
import springmvc.dto.User;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Controller that handles all requests related to the registration of a user
 */
@Controller
public class RegistrationController {

    private Log log = LogFactory.getLog(RegistrationController.class);

    @Autowired
    private UserDao userDao;

    /**
     * Returns the registration page
     * @param user  User information
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String createUser(User user) {
        return "registrationForm";
    }

    /**
     * Endpoint to submit the registration form
     * @param session The session so we can add that the user is logged in
     * @param user User information
     * @param bindingResult Contains any validation errors
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createUser(HttpSession session, @Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        // Check the form for errors
        if (bindingResult.hasErrors()) {
            // redirect the user back to the same page to display the errors
            log.debug("Registration form has errors");
            modelAndView.setViewName("registrationForm");
        } else {
            if (userDao.createUser(user)){
                log.info("User created");
                // Add the userName to the session so we can identify it.
                session.setAttribute("userName", user.userName);
                // Redirect the user back to the overview.
                modelAndView.setViewName("overview");
            } else {
                // TODO add error to display
                log.error("Could not create user");
                modelAndView.setViewName("registrationForm");
            }
        }
        // Return the view to show the user
        return modelAndView;
    }
}
