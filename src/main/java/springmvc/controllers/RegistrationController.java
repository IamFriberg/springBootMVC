package springmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springmvc.dto.User;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String createUser(User user) {
        return "registrationForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        // Check the form for errors
        if (bindingResult.hasErrors()) {
            // redirect the user back to the same page to display the errors
            modelAndView.setViewName("registrationForm");
        } else {
            // TODO register the user in the DB and add details to the session.
            modelAndView.setViewName("overview");
        }
        // Return the view to show the user
        return modelAndView;
    }
}
