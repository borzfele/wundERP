package wundERP.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewControllers {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String renderHome() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String renderLogin(Model model) {
        return "authorization/login";
    }

    @RequestMapping(value = "/workspace", method = RequestMethod.GET)
    public String renderWorkspace(Model model) {
        return "erp";
    }
}
