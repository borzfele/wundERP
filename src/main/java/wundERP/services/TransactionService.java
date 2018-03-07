package wundERP.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wundERP.models.DailyAccount;
import wundERP.models.Transaction;
import wundERP.repositories.TransactionRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> findAllByDailyAccount(DailyAccount dailyAccount) {
        return transactionRepository.findAllByDailyAccount(dailyAccount);
    }

    public int getSumOf(List<Transaction> transactions) {
        int sum = 0;

        for (Transaction transaction : transactions) {
            sum += transaction.getValue();
        }

        return sum;
    }

}
