package wundERP.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wundERP.models.TransactionIssue;

import java.util.List;

public interface TransactionIssueRepository extends JpaRepository<TransactionIssue, Long> {

    List<TransactionIssue> findAll();

    TransactionIssue findByIssueName(String transactionIssueName);

}
