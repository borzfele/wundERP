package wundERP.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wundERP.models.Transaction;
import wundERP.services.DailyAccountService;
import wundERP.services.TransactionService;
import wundERP.services.UserServiceImpl;

import java.util.Calendar;

@Controller
public class TransactionController {

    private final UserServiceImpl userService;
    private final TransactionService transactionService;
    private final DailyAccountService dailyAccountService;

    @Autowired
    public TransactionController(UserServiceImpl userService, TransactionService transactionService, DailyAccountService dailyAccountService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.dailyAccountService = dailyAccountService;
    }

    @RequestMapping(value = "/add-income", method = RequestMethod.GET)
    public String renderIncomeForm(Model model) {
        model.addAttribute("newIncome", new Transaction());
        model.addAttribute("isIncome", true);
        return "create-transaction";
    }

    @RequestMapping(value = "/add-income", method = RequestMethod.POST)
    public String saveIncome(@ModelAttribute Transaction newIncome) {
        newIncome.setValue(Math.abs(newIncome.getValue()));
        newIncome.setDate(Calendar.getInstance());
        newIncome.setOwner(userService.getCurrentUser());
        newIncome.setDailyAccount(dailyAccountService.getLast());
        transactionService.saveTransaction(newIncome);
        return "redirect:/workspace";
    }

    @RequestMapping(value = "/add-expense", method = RequestMethod.GET)
    public String renderExpenseForm(Model model) {
        model.addAttribute("newExpense", new Transaction());
        model.addAttribute("isIncome", false);
        return "create-transaction";
    }

    @RequestMapping(value = "/add-expense", method = RequestMethod.POST)
    public String saveExpense(@ModelAttribute Transaction newExpense) {
        newExpense.setValue(newExpense.getValue() * -1);
        newExpense.setDate(Calendar.getInstance());
        newExpense.setOwner(userService.getCurrentUser());
        newExpense.setDailyAccount(dailyAccountService.getLast());
        transactionService.saveTransaction(newExpense);
        return "redirect:/workspace";
    }
}
