package wundERP.models.comment;

import wundERP.models.Transaction;

public class TransactionComment extends Comment {

    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
