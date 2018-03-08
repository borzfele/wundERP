package wundERP.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wundERP.services.DailyAccountService;

@Controller
public class ViewControllers {
    private final DailyAccountService dailyAccountService;
    private static final Logger logger = LoggerFactory.getLogger(ViewControllers.class);

    @Autowired
    public ViewControllers(DailyAccountService dailyAccountService) {
        this.dailyAccountService = dailyAccountService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String renderHome() {
        logger.info("rendering homepage");
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String renderLogin() {
        logger.info("rendering login page");
        return "authorization/login";
    }

    @RequestMapping(value = "/workspace", method = RequestMethod.GET)
    public String renderWorkspace(Model model) {

        if (dailyAccountService.getLast() == null || dailyAccountService.getLast().isClosed()) {
            model.addAttribute("openingPage", Boolean.TRUE);
        } else {
            model.addAttribute("openingPage", Boolean.FALSE);
        }

        logger.info("rendering workspace");
        return "erp";
    }
}
