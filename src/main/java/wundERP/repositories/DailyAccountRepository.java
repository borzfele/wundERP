package wundERP.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wundERP.models.DailyAccount;

import java.util.ArrayList;

@Repository
public interface DailyAccountRepository extends JpaRepository<DailyAccount, Long> {

    ArrayList<DailyAccount> findAll();

}
