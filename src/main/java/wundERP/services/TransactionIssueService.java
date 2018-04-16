package wundERP.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wundERP.models.TransactionIssue;
import wundERP.repositories.TransactionIssueRepository;

import java.util.List;

@Service
public class TransactionIssueService {

    private final TransactionIssueRepository transactionIssueRepository;

    @Autowired
    public TransactionIssueService(TransactionIssueRepository transactionIssueRepository) {
        this.transactionIssueRepository = transactionIssueRepository;
    }

    public void saveTransactionIssue(TransactionIssue transactionIssue) {
        transactionIssueRepository.save(transactionIssue);
    }

    public List<TransactionIssue> findAll() {
        return transactionIssueRepository.findAll();
    }

    public TransactionIssue findByIssueName(String issueName) {
        return transactionIssueRepository.findByIssueName(issueName);
    }

}
