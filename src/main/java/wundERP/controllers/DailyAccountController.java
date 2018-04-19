package wundERP.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wundERP.models.DailyAccount;
import wundERP.services.DailyAccountService;
import wundERP.services.EmailService;
import wundERP.services.TransactionService;
import wundERP.services.UserServiceImpl;

import java.util.*;

@Controller
public class DailyAccountController {

    @Value( value = "${app.banknotes.huf}")
    private String banknotes;
    private final UserServiceImpl userService;
    private final DailyAccountService dailyAccountService;
    private final TransactionService transactionService;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(ViewControllers.class);

    @Autowired
    public DailyAccountController(UserServiceImpl userService, DailyAccountService dailyAccountService, TransactionService transactionService, EmailService emailService) {
        this.userService = userService;
        this.dailyAccountService = dailyAccountService;
        this.transactionService = transactionService;
        this.emailService = emailService;
    }

    @RequestMapping(value = "/open-day", method = RequestMethod.POST)
    public String saveDayOpen(@RequestParam String openCash) {

        if (dailyAccountService.getLast() == null || dailyAccountService.getLast().isClosed()) {
            DailyAccount dailyAccount = new DailyAccount();
            dailyAccount.setClosed(false);
            dailyAccount.setOwner(userService.getCurrentUser());
            dailyAccount.setOpenDate(Calendar.getInstance());
            dailyAccount.setOpenCash(Integer.valueOf(openCash));
            transactionService.assignTransactionsToNextDailyAccount(transactionService.findAllByAfterClose(true), dailyAccount);
            dailyAccountService.saveDailyAccount(dailyAccount);
            return "redirect:/workspace";
        } else {
            return "redirect:/workspace";
        }

    }

    @RequestMapping(value = "/close-day", method = RequestMethod.POST)
    public String saveDayClose(@RequestParam String closeCash,
                               @RequestParam String terminalBalance,
                               @RequestParam String cassaBalance,
                               @RequestParam String posBalance,
                               @RequestParam String comments) {

        if (!dailyAccountService.getLast().isClosed()) {

            DailyAccount lastOpened = dailyAccountService.getLast();

            lastOpened.setCloseCash(Integer.valueOf(closeCash));
            lastOpened.setTerminalBalance(Integer.valueOf(terminalBalance));
            lastOpened.setCassaBalance(Integer.valueOf(cassaBalance));
            lastOpened.setPosBalance(Integer.valueOf(posBalance));
            lastOpened.setComments(comments);
            lastOpened.setClosed(true);
            int sumOfDailyTransactions = transactionService.getSumOf(transactionService.findAllByDailyAccount(dailyAccountService.findById(lastOpened.getId())));
            int dailyBalance = lastOpened.getCloseCash()
                    + lastOpened.getTerminalBalance()
                    - lastOpened.getOpenCash() + sumOfDailyTransactions;
            lastOpened.setDailyBalance(dailyBalance);
            lastOpened.setCloseDate(Calendar.getInstance());
            dailyAccountService.saveDailyAccount(lastOpened);
            emailService.sendClosingMessage(lastOpened);
            return "redirect:/workspace";

        } else {
            return "redirect:/workspace";
        }
    }
}
