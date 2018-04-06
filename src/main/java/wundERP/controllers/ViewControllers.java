package wundERP.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wundERP.services.DailyAccountService;
import wundERP.services.RoleService;
import wundERP.services.UserServiceImpl;

@Controller
public class ViewControllers {
    private final DailyAccountService dailyAccountService;
    private final UserServiceImpl userService;
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(ViewControllers.class);

    @Autowired
    public ViewControllers(DailyAccountService dailyAccountService, UserServiceImpl userService, RoleService roleService) {
        this.dailyAccountService = dailyAccountService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String renderHome() {
        logger.info("rendering homepage");
        return "redirect:/workspace";
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

            if (dailyAccountService.getLastClosed() == null || dailyAccountService.getLastClosed().getComments().equals("")) {

                model.addAttribute("messages", "Nincs üzenet tegnapról.");

            } else {
                    model.addAttribute("messages", dailyAccountService.getLastClosed().getComments());
            }
        }

        if (userService.getCurrentUser().getRoles().contains(roleService.findByName("admin"))) {
            model.addAttribute("isAdmin", true);
        }

        return "erp";
    }
}
