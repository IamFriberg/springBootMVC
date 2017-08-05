package springmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springmvc.dto.User;

import javax.validation.Valid;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(User user) {
        return "loginForm";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView checkUserInfo(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            // redirect the user back to the same page to display the errors
            modelAndView.setViewName("loginForm");
        } else {
            // TODO add details to the session.
            modelAndView.setViewName("overview");
        }
        return modelAndView;
    }
}
