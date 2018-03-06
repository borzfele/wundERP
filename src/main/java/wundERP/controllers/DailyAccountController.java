package wundERP.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wundERP.models.DailyAccount;
import wundERP.services.DailyAccountService;
import wundERP.services.UserServiceImpl;

import java.util.Calendar;

@Controller
public class DailyAccountController {

    private final UserServiceImpl userService;
    private final DailyAccountService dailyAccountService;
    private static final Logger logger = LoggerFactory.getLogger(ViewControllers.class);

    @Autowired
    public DailyAccountController(UserServiceImpl userService, DailyAccountService dailyAccountService) {
        this.userService = userService;
        this.dailyAccountService = dailyAccountService;
    }

    @RequestMapping(value = "/open-day", method = RequestMethod.GET)
    public String renderDayOpen(Model model) {
        model.addAttribute("dailyAccount", new DailyAccount());
        return "day-open-form";
    }

    @RequestMapping(value = "/open-day", method = RequestMethod.POST)
    public String saveDayOpen(@ModelAttribute DailyAccount dailyAccount) {
        dailyAccount.setClosed(false);
        dailyAccount.setOwner(userService.getCurrentUser());
        dailyAccount.setDate(Calendar.getInstance());
        dailyAccountService.saveDailyAccount(dailyAccount);
        return "redirect:/workspace";
    }

    @RequestMapping(value = "/close-day", method = RequestMethod.GET)
    public String renderDayClose(Model model) {
        model.addAttribute("openedDailyAccount", new DailyAccount());
        return "day-close-form";
    }

    @RequestMapping(value = "/close-day", method = RequestMethod.POST)
    public String saveDayClose(@ModelAttribute DailyAccount dailyAccount) {
        DailyAccount lastOpened = dailyAccountService.getLast();

        lastOpened.setClosed(true);
        lastOpened.setCassaBalance(dailyAccount.getCassaBalance());
        lastOpened.setCloseCash(dailyAccount.getCloseCash());
        lastOpened.setTerminalBalance(dailyAccount.getTerminalBalance());
        lastOpened.setPosBalance(dailyAccount.getPosBalance());
        int dailyBalance = lastOpened.getCloseCash()
                + lastOpened.getTerminalBalance()
                - lastOpened.getOpenCash();
        lastOpened.setDailyBalance(dailyBalance);
        lastOpened.setComments(dailyAccount.getComments());
        dailyAccountService.saveDailyAccount(lastOpened);
        return "redirect:/workspace";
    }
}
