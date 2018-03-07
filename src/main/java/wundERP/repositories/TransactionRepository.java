package wundERP.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wundERP.models.DailyAccount;
import wundERP.models.Transaction;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByDailyAccount(DailyAccount dailyAccount);
}
