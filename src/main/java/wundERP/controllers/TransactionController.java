package wundERP.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/add-income", method = RequestMethod.GET)
    public String renderIncomeForm(Model model) {
        model.addAttribute("newIncome", new Transaction());
        model.addAttribute("isIncome", true);
        model.addAttribute("issueList", transactionIssueService.findAll());

        return "create-transaction";
    }

    @RequestMapping(value = "/add-income", method = RequestMethod.POST)
    public String saveIncome(@ModelAttribute Transaction newIncome) {
        newIncome.setValue(Math.abs(newIncome.getValue()));
        newIncome.setDate(Calendar.getInstance());
        newIncome.setOwner(userService.getCurrentUser());
        newIncome.setDailyAccount(dailyAccountService.getLast());
        if (dailyAccountService.getLast().isClosed() &&
                dailyAccountService.getLast().getCloseDate().before(newIncome.getDate())) {
            newIncome.setAfterClose(true);
        } else {
            newIncome.setAfterClose(false);
        }
        transactionService.saveTransaction(newIncome);
        return "redirect:/workspace";
    }

    @RequestMapping(value = "/add-expense", method = RequestMethod.GET)
    public String renderExpenseForm(Model model) {
        List<TransactionIssue> transactionIssues = transactionIssueService.findAll();
        model.addAttribute("newExpense", new Transaction());
        model.addAttribute("isIncome", false);
        model.addAttribute("issueList", transactionIssues);
        return "create-transaction";
    }

    @RequestMapping(value = "/add-expense", method = RequestMethod.POST)
    public String saveExpense(@ModelAttribute Transaction newExpense) {
        newExpense.setValue(newExpense.getValue() * -1);
        newExpense.setDate(Calendar.getInstance());
        newExpense.setOwner(userService.getCurrentUser());
        newExpense.setDailyAccount(dailyAccountService.getLast());
        if (dailyAccountService.getLast().isClosed() &&
                dailyAccountService.getLast().getCloseDate().before(newExpense.getDate())) {
            newExpense.setAfterClose(true);
        } else {
            newExpense.setAfterClose(false);
        }
        transactionService.saveTransaction(newExpense);
        return "redirect:/workspace";
    }
}
