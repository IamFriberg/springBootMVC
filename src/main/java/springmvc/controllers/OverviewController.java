package springmvc.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller that handles all requests related to the overview
 */
@Controller
public class OverviewController {

    private Log log = LogFactory.getLog(OverviewController.class);

    /**
     * Main method for the overview
     * @return returns the overview
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String overview() {
        return "overview";
    }
}
