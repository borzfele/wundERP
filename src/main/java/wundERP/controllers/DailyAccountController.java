package wundERP.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wundERP.models.CashCount;
import wundERP.models.DailyAccount;
import wundERP.services.DailyAccountService;
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
    private static final Logger logger = LoggerFactory.getLogger(ViewControllers.class);

    @Autowired
    public DailyAccountController(UserServiceImpl userService, DailyAccountService dailyAccountService, TransactionService transactionService) {
        this.userService = userService;
        this.dailyAccountService = dailyAccountService;
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/open-day", method = RequestMethod.POST)
    public String saveDayOpen(@RequestBody List<Integer> cashCount) {
        CashCount openCash = new CashCount();
        int[] numbers = Arrays.stream(banknotes.split(",")).mapToInt(Integer::parseInt).toArray();
        Map<Integer, Integer> countPerBanknotes = new HashMap<>();

        for (int i = 0; i < cashCount.size(); i++) {
            countPerBanknotes.put(numbers[i], cashCount.get(i));
        }

        openCash.setNoteCount(countPerBanknotes);

        DailyAccount dailyAccount = new DailyAccount();
        dailyAccount.setClosed(false);
        dailyAccount.setOwner(userService.getCurrentUser());
        dailyAccount.setOpenDate(Calendar.getInstance());
        dailyAccount.setOpenCash(Integer.valueOf(openCash));
        dailyAccountService.saveDailyAccount(dailyAccount);
        return "redirect:/workspace";
    }

    @RequestMapping(value = "/close-day", method = RequestMethod.POST)
    public String saveDayClose(@RequestParam String closeCash,
                               @RequestParam String terminalBalance,
                               @RequestParam String cassaBalance,
                               @RequestParam String posBalance,
                               @RequestParam String comments) {
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
        return "redirect:/workspace";
    }
}
