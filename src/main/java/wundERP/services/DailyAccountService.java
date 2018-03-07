package wundERP.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wundERP.models.DailyAccount;
import wundERP.repositories.DailyAccountRepository;

@Service
public class DailyAccountService {

    private final DailyAccountRepository dailyAccountRepository;

    @Autowired
    public DailyAccountService(DailyAccountRepository dailyAccountRepository) {
        this.dailyAccountRepository = dailyAccountRepository;
    }

    public DailyAccount findById(long id) {
        return dailyAccountRepository.findById(id);
    }

    public void saveDailyAccount(DailyAccount dailyAccount) {
        dailyAccountRepository.save(dailyAccount);
    }

    public DailyAccount getLast() {

        if (dailyAccountRepository.findAll().size() == 0) {
            return null;
        } else {
            return dailyAccountRepository.findAll().get(dailyAccountRepository.findAll().size() - 1);
        }

    }

}
