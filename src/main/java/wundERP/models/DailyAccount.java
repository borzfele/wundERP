package wundERP.models;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class DailyAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Calendar openDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;
    @Column(nullable = false)
    private int openCash;
    private int dailyBalance;
    private int closeCash;
    private int cassaBalance;
    private int posBalance;
    private int terminalBalance;
    private String comments;
    private boolean isClosed;
    private Calendar closeDate;

    public Calendar getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Calendar closeDate) {
        this.closeDate = closeDate;
    }

    public Calendar getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Calendar openDate) {
        this.openDate = openDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getOpenCash() {
        return openCash;
    }

    public void setOpenCash(int openCash) {
        this.openCash = openCash;
    }

    public int getDailyBalance() {
        return dailyBalance;
    }

    public void setDailyBalance(int dailyBalance) {
        this.dailyBalance = dailyBalance;
    }

    public int getCloseCash() {
        return closeCash;
    }

    public void setCloseCash(int closeCash) {
        this.closeCash = closeCash;
    }

    public int getCassaBalance() {
        return cassaBalance;
    }

    public void setCassaBalance(int cassaBalance) {
        this.cassaBalance = cassaBalance;
    }

    public int getPosBalance() {
        return posBalance;
    }

    public void setPosBalance(int posBalance) {
        this.posBalance = posBalance;
    }

    public int getTerminalBalance() {
        return terminalBalance;
    }

    public void setTerminalBalance(int terminalBalance) {
        this.terminalBalance = terminalBalance;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}