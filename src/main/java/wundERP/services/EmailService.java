package wundERP.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import wundERP.models.DailyAccount;
import wundERP.models.Transaction;

import java.util.Arrays;

@Service
    public class EmailService {

        private final static Logger logger = LoggerFactory.getLogger(EmailService.class);
        private final JavaMailSender mailSender;
        private UserServiceImpl userService;
        @Value("${spring.mail.username}")
        private String MAIL_FROM;
        @Value("${app.mail.recipients}")
        private String[] recipients;

        @Autowired
        public EmailService(JavaMailSender mailSender, UserServiceImpl userService) {
            this.mailSender = mailSender;
            this.userService = userService;
        }

        public void sendClosingMessage(DailyAccount dailyAccount) {
            SimpleMailMessage mail;
            StringBuilder textBuilder = new StringBuilder();

            textBuilder.append("Colleague: ");
            textBuilder.append(dailyAccount.getOwner().getName());
            textBuilder.append("\n");

            textBuilder.append("Opened at: ");
            textBuilder.append(dailyAccount.getOpenDate().getTime());
            textBuilder.append("\n");

            textBuilder.append("Opening cash: ");
            textBuilder.append(dailyAccount.getOpenCash());
            textBuilder.append("\n\n");

            textBuilder.append("Transactions: ");
            textBuilder.append("\n");

            if (dailyAccount.getTransactions().size() != 0) {
                for (Transaction transaction : dailyAccount.getTransactions()) {
                    textBuilder.append("\tDate: ");
                    textBuilder.append(transaction.getDate().getTime());
                    textBuilder.append("\n");
                    textBuilder.append("\tValue: ");
                    textBuilder.append(transaction.getValue());
                    textBuilder.append("\n");
                    textBuilder.append("\tIssue: ");
                    textBuilder.append(transaction.getIssue().getIssueName());
                    textBuilder.append("\n");
                    textBuilder.append("\tComments: ");
                    textBuilder.append(transaction.getDescription());
                    textBuilder.append("\n");
                    textBuilder.append("\tIs Bank Transaction: ");
                    textBuilder.append(transaction.isBankTransaction());
                    textBuilder.append("\n\n");
                }
            } else {
                textBuilder.append("\t-");
                textBuilder.append("\n\n");
            }

            textBuilder.append("Closing Time: ");
            textBuilder.append(dailyAccount.getCloseDate().getTime());
            textBuilder.append("\n");

            textBuilder.append("Closing Cash: ");
            textBuilder.append(dailyAccount.getCloseCash());
            textBuilder.append("\n");

            textBuilder.append("Terminal Balance: ");
            textBuilder.append(dailyAccount.getTerminalBalance());
            textBuilder.append("\n");

            textBuilder.append("Cassa Balance: ");
            textBuilder.append(dailyAccount.getCassaBalance());
            textBuilder.append("\n");

            textBuilder.append("POS Balance: ");
            textBuilder.append(dailyAccount.getPosBalance());
            textBuilder.append("\n");

            textBuilder.append("Not Documented Income: ");
            textBuilder.append(dailyAccount.getCloseCash() + dailyAccount.getTerminalBalance() - dailyAccount.getCassaBalance());
            textBuilder.append("\n");

            textBuilder.append("Total Balance: ");
            textBuilder.append(dailyAccount.getDailyBalance());
            textBuilder.append("\n");

            textBuilder.append("Comments: ");
            textBuilder.append(dailyAccount.getComments());

            try {
                logger.info(Arrays.toString(recipients));
                mail = new SimpleMailMessage();
                mail.setFrom(MAIL_FROM);
                mail.setTo(recipients);
                mail.setSubject("Closing happened");
                mail.setText(textBuilder.toString());
                mailSender.send(mail);

            } catch (Exception e) {
                logger.error("wrong email address: " + Arrays.toString(recipients));
            }
        }
    }
