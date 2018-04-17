package wundERP.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wundERP.models.Role;
import wundERP.models.Transaction;
import wundERP.models.TransactionIssue;
import wundERP.models.User;
import wundERP.services.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class DBPopulator {

    public DBPopulator(TransactionIssueService transactionIssueService, TransactionService transactionService, RoleService roleService, UserServiceImpl userService) {

        Logger log = LoggerFactory.getLogger(DBPopulator.class);
        PasswordEncoder encriptor = new BCryptPasswordEncoder();

        TransactionIssue transactionIssue = new TransactionIssue();
        transactionIssue.setIssueName("iszi");
        transactionIssueService.saveTransactionIssue(transactionIssue);

        User user = new User();
        user.setName("Melák");
        user.setPassword("Melák");
        User user2 = new User();
        user2.setName("Buzi");
        user2.setPassword("vagyok");

        Role userRole = roleService.findByName("user");
        Role adminRole = roleService.findByName("admin");

        if (adminRole != null) {
            user.getRoles().add(adminRole);
        } else {
            Role freshlyCreatedAdminRole = new Role();
            freshlyCreatedAdminRole.setName("admin");
            roleService.saveRole(freshlyCreatedAdminRole);
            user2.getRoles().add(freshlyCreatedAdminRole);
        }

        if (userRole != null) {
            user.getRoles().add(userRole);
        } else {
            Role freshlyCreatedUserRole = new Role();
            freshlyCreatedUserRole.setName("user");
            roleService.saveRole(freshlyCreatedUserRole);
            user.getRoles().add(freshlyCreatedUserRole);
        }

        user2.setPassword(encriptor.encode(user2.getPassword()));
        user.setPassword(encriptor.encode(user.getPassword()));

        userService.saveUser(user);
        userService.saveUser(user2);

    }

}

