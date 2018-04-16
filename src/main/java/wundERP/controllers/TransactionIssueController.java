package wundERP.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import wundERP.models.TransactionIssue;
import wundERP.models.TransactionIssueResponseBody;
import wundERP.services.TransactionIssueService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TransactionIssueController {

    private final TransactionIssueService transactionIssueService;

    @Autowired
    public TransactionIssueController(TransactionIssueService transactionIssueService) {
        this.transactionIssueService = transactionIssueService;
    }

    @GetMapping("/get-issues")
    public ResponseEntity<?> getIssues() {

        TransactionIssueResponseBody result = new TransactionIssueResponseBody();

        List<String> transactionIssues = new ArrayList<>();

        for (TransactionIssue transactionIssue : transactionIssueService.findAll()) {
            transactionIssues.add(transactionIssue.getIssueName());
        }

        result.setTransactionIssues(transactionIssues);

        return ResponseEntity.ok(result);

    }

}
