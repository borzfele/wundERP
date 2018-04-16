package wundERP.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wundERP.models.Transaction;
import wundERP.models.TransactionIssue;
import wundERP.services.*;

import java.util.Calendar;
import java.util.List;

@Controller
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final UserServiceImpl userService;
    private final TransactionService transactionService;
    private final DailyAccountService dailyAccountService;
    private final TransactionIssueService transactionIssueService;
    private final RoleService roleService;

    @Autowired
    public TransactionController(UserServiceImpl userService, TransactionService transactionService, DailyAccountService dailyAccountService, TransactionIssueService transactionIssueService, RoleService roleService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.dailyAccountService = dailyAccountService;
        this.transactionIssueService = transactionIssueService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/add-income", method = RequestMethod.POST)
    public String saveIncome(@RequestParam String value,
                             @RequestParam String description,
                             @RequestParam(required =false, value ="isBank") String isBank,
                             @RequestParam String issue) {

        Transaction newIncome = new Transaction();

        newIncome.setValue(Math.abs(Integer.valueOf(value)));
        newIncome.setDescription(description);
        newIncome.setDate(Calendar.getInstance());
        newIncome.setOwner(userService.getCurrentUser());
        if (dailyAccountService.getLast() != null) {
            newIncome.setDailyAccount(dailyAccountService.getLast());
        }
        if (dailyAccountService.getLast()!= null && dailyAccountService.getLast().isClosed() &&
                dailyAccountService.getLast().getCloseDate().before(newIncome.getDate())) {
            newIncome.setAfterClose(true);
        } else {
            newIncome.setAfterClose(false);
        }
        if (isBank != null) {
            newIncome.setBankTransaction(true);
        } else {
            newIncome.setBankTransaction(false);
        }
        newIncome.setIssue(transactionIssueService.findByIssueName(issue));
        transactionService.saveTransaction(newIncome);
        return "redirect:/workspace";
    }

    @RequestMapping(value = "/add-expense", method = RequestMethod.POST)
    public String saveExpense(@RequestParam(value = "isBank", required = false) String isBank,
                              @RequestParam String value,
                              @RequestParam String description,
                              @RequestParam String issue) {

        Transaction newExpense = new Transaction();

        newExpense.setValue(Math.abs(Integer.valueOf(value)) * -1);
        newExpense.setDescription(description);
        newExpense.setDate(Calendar.getInstance());
        newExpense.setOwner(userService.getCurrentUser());
        if (dailyAccountService.getLast() != null) {
            newExpense.setDailyAccount(dailyAccountService.getLast());
        }
        if (dailyAccountService.getLast()!= null && dailyAccountService.getLast().isClosed() &&
                dailyAccountService.getLast().getCloseDate().before(newExpense.getDate())) {
            newExpense.setAfterClose(true);
        } else {
            newExpense.setAfterClose(false);
        }
        if (isBank != null) {
            newExpense.setBankTransaction(true);
        } else {
            newExpense.setBankTransaction(false);
        }
        newExpense.setIssue(transactionIssueService.findByIssueName(issue));
        transactionService.saveTransaction(newExpense);
        return "redirect:/workspace";
    }
}
