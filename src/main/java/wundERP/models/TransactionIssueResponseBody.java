package wundERP.models;

import java.util.List;

public class TransactionIssueResponseBody {

    private List<String> transactionIssues;
    private String msg;

    public List<String> getTransactionIssues() {
        return transactionIssues;
    }

    public void setTransactionIssues(List<String> transactionIssues) {
        this.transactionIssues = transactionIssues;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
