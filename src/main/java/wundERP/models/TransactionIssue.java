package wundERP.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transaction_issues")
public class TransactionIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "issue_name")
    private String issueName;
    @OneToMany(mappedBy = "issue")
    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }
}
